package DM19S1;

import java.util.*;

class Util{
    String readFile(String fileName) {
        return null;

    }
    void wrieteFile(String fileName,String content) {
     }
    String format(String formatString,String delimiter) {
        return null;
    }

    public static Donator getDonatorFromInstruction(String line){
        String [] record = line.split(";");
        Set<Donator> donatorSet = getDonatorSet(Arrays.asList(record));
        Donator donator = null;
        if (donatorSet.size() > 0){
            donator =  donatorSet.iterator().next();
        }else{
            System.out.println("有一条非法指令："+line);
        }
        return donator;
    }
    public static boolean isValid(String line) {
        Set<String> instructionSet = new HashSet<>();
        instructionSet.add("update");
        instructionSet.add("delete");
        instructionSet.add("query");
        instructionSet.add("donate");
        String instruction = line.split("\\s")[0];
        if (instructionSet.contains(instruction.toLowerCase())){
            return true;
        }
        return false;
    }

    public static  String getValueByName(String nameAndValue,String filedName){
        return nameAndValue.substring(filedName.length()).trim();
    }

    public static Donator getDonatorFromString(Map<String,String> record){
        Donator donator = new Donator();
        for (String name: record.keySet()) {
            switch (name.toLowerCase()){
                case "name":
                    donator.setName(record.get("name"));
                    break;
                case "birthday" :
                    donator.setBirthday(record.get("birthday"));
                    break;
                case "phone":
                    donator.setPhone(record.get("phone"));
                    break;
                case "recipient" :
                    donator.setRecipients(getRecipients(record.get("recipient"),record.get("donation"),record.get("postcode")));
                    break;
                case "address" :
                    donator.setAddress(record.get("address"));
                    break;
                case "postcode" :
                    donator.setPostcode(record.get("postcode"));
                    break;
                default:
                    break;
            }
        }
        return  donator;
    }

    public static List<Recipient> getRecipients(String name,String money,String postCode){
        String [] names = name.split(",");
        String [] moneys = money.split(",");
        List<Recipient> recipients = new ArrayList<>();
        if (names.length != moneys.length){
            return  recipients;
        }
        for (int i = 0; i< names.length; i++){
               Recipient recipient = new Recipient(names[i],moneys[i],postCode);
               recipients.add(recipient);
        }
       return recipients;
    }
    public static Set<Donator> getDonatorSet(List<String> result){
        Map<String,String> don = new HashMap<>();
        Set<Donator> record = new HashSet<>();
        for (int i = 0; i<= result.size(); i++) {
            if (i == result.size()){
                addRecord(don,record);
                don.clear();
                continue;
            }
            String line = result.get(i).trim();
            if (line.equals("") ){
                addRecord(don,record);
                don.clear();
                continue;
            }
            String name = line.split("\\s")[0];
            String value = Util.getValueByName(line, name);
            don.put(name,value);
        }
        return record;
    }
    private static void addRecord(Map<String,String> don,Set<Donator> record){
        Donator donatorStr = Util.getDonatorFromString(don);
        if(donatorStr.getRecipients() == null){
            donatorStr.setRecipients(new ArrayList<>());
        }
        if(donatorStr.isValidToAdd()){
            record.add(donatorStr);
        }
        don.clear();
    }
}