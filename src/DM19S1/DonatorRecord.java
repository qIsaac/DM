package DM19S1;

import java.io.*;
import java.util.*;

public class DonatorRecord {
    private List<Donator>  donatorList;

    public DonatorRecord(){
        donatorList = new ArrayList<>();
    }

    public void updateDonator(String s, Set<Donator> recorde){
        Instruction instruction = new InstrunctionProcessor();
//        instruction.update(s,recorde);
    }
    public void delDonator(String s, Set<Donator> recorde){
        Instruction instruction = new InstrunctionProcessor();
//        instruction.update(s,recorde);
    }

    public Set<Donator> readFormInputFile(String fileName){
        List<String> result = new ArrayList<>();
        try {
            result = FileUtils.readLines(new File(fileName),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Util.getDonatorSet(result);
    }
}
