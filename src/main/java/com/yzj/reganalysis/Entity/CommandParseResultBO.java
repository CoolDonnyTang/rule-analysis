package com.yzj.reganalysis.Entity;

import lombok.Data;

import java.util.List;

@Data
public class CommandParseResultBO {
    private List<List<String>> allStatus;
    private List<Command> allCommand;
    private List<String> allCommandString;
}
