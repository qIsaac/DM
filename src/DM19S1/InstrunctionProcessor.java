package DM19S1;

import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.*;

public class InstrunctionProcessor implements Instruction {

    public int update(String donator, Set<Donator> recorde,List<String> lists) {
        Donator donatorFromInstruction = Util.getDonatorFromInstruction(donator);
        StringBuilder sb = new StringBuilder();
        sb.append("-----").append("update ").append(donator.split("[;]")[0]).append("------\n");
        if (donatorFromInstruction == null){
            sb.append("Invalid instruction!\n");
            sb.append("-----------------------------\n");
            lists.add(sb.toString());
            return 0;
        }
        if (recorde.contains(donatorFromInstruction)){
            recorde.forEach( obj->{
                if (obj.getBirthday().equals(donatorFromInstruction.getBirthday()) && obj.getName().equals(donatorFromInstruction.getName())){
                    if (donatorFromInstruction.getPostcode() != null){
                        obj.setPostcode(donatorFromInstruction.getPostcode());
                    }
                    if (donatorFromInstruction.getAddress() != null){
                        obj.setAddress(donatorFromInstruction.getAddress());
                    }
                    if (donatorFromInstruction.getPhone() != null){
                        obj.setPhone(donatorFromInstruction.getPhone());
                    }
                    sb.append("Record updated!\n");
                }
            });
        }else{
            recorde.add(donatorFromInstruction);
            sb.append("Record added!\n");
        }
        sb.append("-----------------------------\n");
        lists.add(sb.toString());
        return 0;
    }

    public int donate(String recipient,Set<Donator> recorde,List<String> lists) {
        StringBuilder sb = new StringBuilder();
        String [] recipients = recipient.split(";");
        if (recipients.length != 4 ){
            return 0;
        }
        sb.append("-----").append("donate").append(recipients[0]).append("------\n");
        Donator donator = new Donator();
        donator.setName(recipients[0].trim());
        donator.setBirthday(recipients[1].trim());
        if (donator.isDelValidToAdd()){
            if (recorde.contains(donator)){
                recorde.forEach( obj->{
                    if (obj.getBirthday().equals(donator.getBirthday()) && obj.getName().equals(donator.getName())){
                        for ( int i = 2 ; i < recipients.length; i++){
                            String[] donates = recipients[i].split(",");
                            Recipient newRec = new Recipient(donates[0],donates[1]);
                            obj.getRecipients().add(newRec);
                        }
                    }
                });
                sb.append((recipients.length -2)+" new donation record(s) for"+recipients[0].trim()+"  "+recipients[1].trim()+"\n");
            }else{
                sb.append("Invalid instruction!\n");
            }
        }
        sb.append("-----------------------------\n");
        lists.add(sb.toString());
        return 1;
    }

    public int delete(String name, Set<Donator> recorde,List<String> lists) {
        StringBuilder sb = new StringBuilder();
        String [] lines = name.split(";");
        if (lines.length != 2 ){
            return 0;
        }
        sb.append("-----").append("delete").append(lines[0]).append("------\n");
        Donator donator = new Donator();
        donator.setName(lines[0].trim());
        donator.setBirthday(lines[1].trim());
        if (donator.isDelValidToAdd()){
            boolean remove = recorde.remove(donator);
            if (remove){
                sb.append("Record deleted!\n");
            }else{
                sb.append("Record not found!\n");
            }
        }else{
            sb.append("Invalid instruction!\n");
        }
        sb.append("-----------------------------\n");
        lists.add(sb.toString());
        return 1;
    }

    public int query(String name) {
        return 0;
    }

    public int query(int n) {
        return 0;
    }

    public int query() {
        return 0;
    }

    public void process(String line,Set<Donator> recorde,List<String> lists){
        if (line != null && !"".equals(line) && !Util.isValid(line)){
            System.out.println("非法指令： " + line);
        }
        if (line.startsWith("update")){
            update(line.substring(6).trim(),recorde,lists);
        }
        if (line.startsWith("delete")){
            delete(line.substring(6),recorde,lists);
        }
        if (line.startsWith("query")){

        }
        if (line.startsWith("donate")){
            donate(line.substring(6),recorde,lists);
        }
    }



}
