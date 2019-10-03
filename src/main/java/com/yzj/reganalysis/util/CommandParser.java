package com.yzj.reganalysis.util;

import com.yzj.reganalysis.Entity.*;
import com.yzj.reganalysis.bo.RangeCmdParamBO;
import lombok.Data;
import org.apache.el.stream.StreamELResolverImpl;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class CommandParser {
    public static final String PART = "part";
    public static final String RANGE = "range";
    public static final String SIMPLE_OR = "simpleOR";
    public static final String MIN = "min";
    public static final String MAX = "max";
    public static final String SEPARATOR = "_";
    public static final String LABEL_PREFIX = "LABEL_";
    public static final String LABEL_DEFAULT = "DEFAULT";
    public static final String LABEL_ACTION = "ACTION";
    public static final String CMD_TYPE_DEFAULT = "rst_n";
    public static final String CMD_TYPE_SLT = "SLT";
    public static final String CMD_TYPE_BEQ = "BEQ";
    public static final String CMD_TYPE_BNE = "BNE";
    public static final String CMD_TYPE_NOP = "NOP";
    public static final String CMD_TYPE_ADDI = "ADDI";
    public static final String CMD_TYPE_J = "J";
    private List<IpPart> allIpAndPortPart;
    IpAddrStatus source;
    IpAddrStatus target;
    ActionPart action;

    public CommandParser(IpAddrStatus source, IpAddrStatus target, ActionPart action) {
        List<IpPart> allIpAndPortPart = new ArrayList<>();
        allIpAndPortPart.addAll(source.getParts());
        allIpAndPortPart.addAll(target.getParts());

        this.allIpAndPortPart = allIpAndPortPart;
        this.source = source;
        this.target = target;
        this.action = action;
        init();
    }

    private void init() {
        allIpAndPortPart = new ArrayList<>();
        allIpAndPortPart.addAll(source.getParts());
        allIpAndPortPart.addAll(target.getParts());
    }

    public List<Command> parse() {
        List<Command> result = new ArrayList<>();

        //add default
        Command defaultCmd = new Command();
        defaultCmd.setType(CMD_TYPE_DEFAULT);
        defaultCmd.setLabel(LABEL_DEFAULT);
        result.add(defaultCmd);

        for(int partIndex=0; partIndex<allIpAndPortPart.size(); partIndex++) {
            IpPart currentPart = allIpAndPortPart.get(partIndex);

            //true:还存在下一个part
            boolean hasNextPart = !(partIndex == allIpAndPortPart.size()-1);
            //true:下一个part存在range判断
            boolean nextPartHasRange = hasNextPart&&!CollectionUtils.isEmpty(allIpAndPortPart.get(partIndex+1).getRange());
            //true:还存在简单‘或’判断
            boolean hasSimpleOrInCurrentPart = !CollectionUtils.isEmpty(currentPart.getStatus());

            //生成range命令
            Set<Range> rangeSet = currentPart.getRange();
            if(!CollectionUtils.isEmpty(rangeSet)) {
                List<Range> partRange = new ArrayList<>(rangeSet);
                for(int rangeIndex=0; rangeIndex<partRange.size(); rangeIndex++) {
                    //true:在当前part中还存在下一个range判断
                    boolean hasNextRange = !(rangeIndex == partRange.size()-1);

                    //label中的序号从1开始
                    String currentRangeLabelPrefix = LABEL_PREFIX+PART+(partIndex+1)+SEPARATOR+RANGE+(rangeIndex+1)+SEPARATOR;
                    Range range = partRange.get(rangeIndex);
                    int min = range.getMin();
                    int max = range.getMax();

                    //MIN cmd
                    result.addAll(getRangCMD(partIndex, rangeIndex, currentPart, MIN, min+"", hasNextRange, hasSimpleOrInCurrentPart, hasNextPart, nextPartHasRange));
                    //MAX cmd
                    result.addAll(getRangCMD(partIndex, rangeIndex, currentPart, MAX, max+"", hasNextRange, hasSimpleOrInCurrentPart, hasNextPart, nextPartHasRange));

//                    //for min start...
//                    Command minCmdSTL = new Command(currentRangeLabelPrefix+MIN, CMD_TYPE_SLT, currentPart.getTempReg(), currentPart.getTargetReg(), min+"");
//                    result.add(minCmdSTL);
//                    //比较通过则调至max处
//                    Command minCmdBEQ = new Command(null, CMD_TYPE_BEQ, currentPart.getTempReg(), currentPart.getZeroReg(), currentRangeLabelPrefix+MAX);
//                    result.add(minCmdBEQ);
//                    Command minCmdNOP = new Command(null, CMD_TYPE_NOP, null, null, null);
//                    result.add(minCmdNOP);
//                    /**
//                     * 立即数不小于min，此时不会跳转到判断max的地方，需要跳转到下一个范围判断或者简单‘或’判断（如1|2|5）
//                     * 如果没有下一个范围判断且没有下一个简单‘或’判断则跳转到default
//                     * 注意：此处的下一个并非下一个part，而是同part中的下一个判断
//                     */
//                    if(hasNextRange) { //存在下一个范围判断则跳转到下一个范围判断
//                        //label中的序号从1开始
//                        String nextRangeLabel = LABEL_PREFIX+PART+(partIndex+1)+SEPARATOR+RANGE+(rangeIndex+2)+SEPARATOR+MIN;
//                        Command minCmdJ = new Command(null, CMD_TYPE_J, null, null, nextRangeLabel);
//                        result.add(minCmdJ);
//                    } else if(hasSimpleOrInCurrentPart) {//不存在下一个范围判断但存在简单‘或’判断则跳转至简单‘或’判断
//                        //label中的序号从1开始
//                        String nextSimpleOrLabel = LABEL_PREFIX+PART+(partIndex+1)+SEPARATOR+SIMPLE_OR;
//                        Command minCmdJ = new Command(null, CMD_TYPE_J, null, null, nextSimpleOrLabel);
//                        result.add(minCmdJ);
//                    } else {
//                        Command minCmdJ = new Command(null, CMD_TYPE_J, null, null, LABEL_DEFAULT);
//                        result.add(minCmdJ);
//                    }
//                    //for min end!!!
//
//                    //for max start...
//
//                    //for max end!!!
                }
            }

            //生成simple or命令
            List<Integer> status = new ArrayList<>(currentPart.getStatus());
            if(!CollectionUtils.isEmpty(status)) {
                for(int i=0; i<status.size(); i++) {
                    String label = LABEL_PREFIX+PART+(partIndex+1)+SEPARATOR+SIMPLE_OR+(i+1);
                    Command cmdADDI = new Command(label, CMD_TYPE_ADDI, currentPart.getTempReg(), currentPart.getZeroReg(), status.get(i).toString());

                    String nexLabel;
                    if(hasNextPart) {//比较通过则跳至下一个part,如果没有下一个part则跳转到ACTION LABEL
                        if(nextPartHasRange) {//跳转至range第一行
                            nexLabel = LABEL_PREFIX+PART+(partIndex+2)+SEPARATOR+RANGE+"1"+SEPARATOR+MIN;
                        } else {//跳转至下一个part简单‘或’判断第一行
                            nexLabel = LABEL_PREFIX+PART+(partIndex+2)+SEPARATOR+SIMPLE_OR+"1";
                        }
                    } else {
                        nexLabel = LABEL_ACTION;
                    }
                    Command cmdBEQ = new Command(null, CMD_TYPE_BEQ, currentPart.getTargetReg(), currentPart.getTempReg(), nexLabel);
                    Command cmdNOP = new Command(null, CMD_TYPE_NOP, null, null, null);
                    result.add(cmdADDI);
                    result.add(cmdBEQ);
                    result.add(cmdNOP);
                }
                Command cmdJ = new Command(null, CMD_TYPE_J, null, null, LABEL_DEFAULT);
                result.add(cmdJ);
            }
        }

        //添加Action的指令
        Command actionCmdADDI = new Command(LABEL_ACTION, CMD_TYPE_ADDI, action.getTargetReg(), action.getZeroReg(), action.getType().toString());
        result.add(actionCmdADDI);

        return result;
    }

    private List<Command> getRangCMD(int partIndex, int rangeIndex, IpPart currentPart, String type, String value, boolean hasNextRange, boolean hasSimpleOrInCurrentPart, boolean hasNextPart, boolean nextPartHasRange) {
        List<Command> result = new ArrayList<>();

        //label中的序号从1开始
        String currentRangeLabelPrefix = LABEL_PREFIX+PART+(partIndex+1)+SEPARATOR+RANGE+(rangeIndex+1)+SEPARATOR;

        Command minCmdSTL = new Command(currentRangeLabelPrefix+type, CMD_TYPE_SLT, currentPart.getTempReg(), currentPart.getTargetReg(), value);
        result.add(minCmdSTL);
        if(MIN.equals(type)) {//对于MIN,比较通过则调至max处
            Command minCmdBEQ = new Command(null, CMD_TYPE_BEQ, currentPart.getTempReg(), currentPart.getZeroReg(), currentRangeLabelPrefix+MAX);
            result.add(minCmdBEQ);
        } else if(MAX.equals(type)) { //对于MAX,比较通过则跳至下一个part,如果没有下一个part则跳转到ACTION LABEL
            String nexLabel = "";
            if(hasNextPart) {
                if(nextPartHasRange) {//跳转至range第一行
                    nexLabel = LABEL_PREFIX+PART+(partIndex+2)+SEPARATOR+RANGE+(1)+SEPARATOR+MIN;
                } else {//跳转至下一个part简单‘或’判断第一行
                    nexLabel = LABEL_PREFIX+PART+(partIndex+2)+SEPARATOR+SIMPLE_OR+"1";
                }
            } else {
                nexLabel = LABEL_ACTION;
            }
            Command minCmdBEQ = new Command(null, CMD_TYPE_BNE, currentPart.getTempReg(), currentPart.getZeroReg(), nexLabel);
            result.add(minCmdBEQ);
        }
        Command minCmdNOP = new Command(null, CMD_TYPE_NOP, null, null, null);
        result.add(minCmdNOP);
        /**
         * 立即数不小于min，此时不会跳转到判断max的地方，需要跳转到下一个范围判断或者简单‘或’判断（如1|2|5）
         * 如果没有下一个范围判断且没有下一个简单‘或’判断则跳转到default
         * 注意：此处的下一个并非下一个part，而是同part中的下一个判断
         */
        if(hasNextRange) { //存在下一个范围判断则跳转到下一个范围判断
            //label中的序号从1开始
            String nextRangeLabel = LABEL_PREFIX+PART+(partIndex+1)+SEPARATOR+RANGE+(rangeIndex+2)+SEPARATOR+MIN;
            Command minCmdJ = new Command(null, CMD_TYPE_J, null, null, nextRangeLabel);
            result.add(minCmdJ);
        } else if(hasSimpleOrInCurrentPart) {//不存在下一个范围判断但存在简单‘或’判断则跳转至简单‘或’判断
            //label中的序号从1开始,跳转至简单或第一行
            String nextSimpleOrLabel = LABEL_PREFIX+PART+(partIndex+1)+SEPARATOR+SIMPLE_OR+"1";
            Command minCmdJ = new Command(null, CMD_TYPE_J, null, null, nextSimpleOrLabel);
            result.add(minCmdJ);
        } else { //没有下一个范围判断且没有下一个简单‘或’判断则跳转到default
            Command minCmdJ = new Command(null, CMD_TYPE_J, null, null, LABEL_DEFAULT);
            result.add(minCmdJ);
        }
        //for min end!!!

        return result;
    }

}
