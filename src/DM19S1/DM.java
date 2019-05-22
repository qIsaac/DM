package DM19S1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DM {
    public static void main(String[] args) {
        if (args.length !=4){
            System.out.println("Please enter the correct parameters ：recordfile instructionfile resultfile reportfile" );
            System.exit(-1);
        }
        String recordfile = args[0];
        String instructionfile = args [1];
        String resultfile = args [2];
        String reportfile = args [3];

        DonatorRecord dr = new DonatorRecord();
        Instruction is = new InstrunctionProcessor();
        Set<Donator> record = dr.readFormInputFile(recordfile);
        List<String> reportList = new ArrayList<>();
        try {
            List<String> instrunctions = FileUtils.readLines(new File(instructionfile),"utf-8");
            instrunctions.forEach( obj ->{
                is.process(obj,record,reportList);
            });
            StringBuilder builder = new StringBuilder();
            for (Donator rec : record) {
                builder.append(rec.toString()+ "\r\n");
            }
            StringBuilder builder1 = new StringBuilder();
            for (String rep : reportList) {
                builder1.append(rep.toString() +"\r\n");
            }
            FileUtils.write(new File(resultfile),builder.toString(),false);
            FileUtils.write(new File(reportfile),builder1.toString(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
