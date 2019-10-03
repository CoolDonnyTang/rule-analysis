package com.yzj.reganalysis.bo;

import com.yzj.reganalysis.Entity.IpPart;
import lombok.Data;

@Data
public class RangeCmdParamBO {
    int partIndex;
    int rangeIndex;
    IpPart currentPart;
    boolean hasNextRange;
    boolean hasSimpleOrInCurrentPart;
    boolean hasNextPart;
    boolean nextPartHasRange;
}
