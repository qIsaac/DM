package DM19S1;


import java.util.*;
import java.util.stream.Collectors;

public class InstrunctionProcessor implements Instruction {

    public int update(String donator, Set<Donator> recorde,List<String> lists) {
        Donator donatorFromInstruction = Util.getDonatorFromInstruction(donator);
        StringBuilder sb = new StringBuilder();
        sb.append("-----").append("update ").append(donator.split("[;]")[0]).append("------\r\n");
        if (donatorFromInstruction == null){
            sb.append("Invalid instruction!\r\n");
            sb.append("-----------------------------\r\n");
            lists.add(sb.toString());
            return 0;
        }
        if (recorde.size() == 0){
            recorde.add(donatorFromInstruction);
            sb.append("Record added!\r\n");
;        }else{
            boolean isUpdate = false;
           for (Donator obj : recorde){
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
                       sb.append("Record updated!\r\n");
                       isUpdate = true;
                       break;
                   }
           }
           if (!isUpdate){
               recorde.add(donatorFromInstruction);
               sb.append("Record added!\r\n");
           }
        }
        sb.append("-----------------------------\r\n");
        lists.add(sb.toString());
        return 0;
    }

    public int donate(String recipient,Set<Donator> recorde,List<String> lists) {
        StringBuilder sb = new StringBuilder();
        String [] recipients = recipient.split(";");
        if (recipients.length < 3 ){
            return 0;
        }
        sb.append("-----").append("donate ").append(recipients[0]).append("------\r\n");
        Donator donator = new Donator();
        donator.setName(recipients[0].trim());
        donator.setBirthday(recipients[1].trim());
        boolean isSuccess = false;
        if (donator.isDelValidToAdd()){
            for (Donator obj : recorde) {
                if (obj.getBirthday().equals(donator.getBirthday()) && obj.getName().equals(donator.getName())){
                    for ( int i = 2 ; i < recipients.length; i++){
                        String[] donates = recipients[i].split(",");
                        Recipient newRec = new Recipient(donates[0].trim(),Long.parseLong(donates[1].trim()),obj.getPostcode());
                        obj.getRecipients().add(newRec);
                        isSuccess  = true;
                    }
                }
            }
            if (isSuccess){
                sb.append((recipients.length -2)+" new donation record(s) for "+recipients[0].trim()+" birthday "+recipients[1].trim()+"\r\n");
            }else{
                sb.append("Invalid instruction!\r\n");
            }
        }
        sb.append("-----------------------------\r\n");
        lists.add(sb.toString());
        return 1;
    }

    public int delete(String name, Set<Donator> recorde,List<String> lists) {
        StringBuilder sb = new StringBuilder();
        String [] lines = name.split(";");
        if (lines.length != 2 ){
            return 0;
        }
        sb.append("-----").append("delete ").append(lines[0]).append("------\r\n");
        Donator donator = new Donator();
        donator.setName(lines[0].trim());
        donator.setBirthday(lines[1].trim());
        if (donator.isDelValidToAdd()){
            boolean remove = recorde.remove(donator);
            if (remove){
                sb.append("Record deleted!\r\n");
            }else{
                sb.append("Record not found!\r\n");
            }
        }else{
            sb.append("Invalid instruction!\r\n");
        }
        sb.append("-----------------------------\r\n");
        lists.add(sb.toString());
        return 1;
    }

    @Override
    public int query(String name, Set<Donator> recorade, List<String> lists) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----").append("query name ").append(name).append("------\r\n");
        Set<Donator> querySet = recorade.stream().filter(o -> name.equals(o.getName())).collect(Collectors.toSet());
        sb.append( querySet.size() + " record(s) found:\n");
        querySet.forEach( o->{
            sb.append(o.toString());
        });
        sb.append("-----------------------------\r\n");
        lists.add(sb.toString());
        return 0;
    }

    @Override
    public int query(int n, List<Donation> donations, List<String> lists) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----").append("query top ").append(n).append("------\r\n");
        for (int i = 0; i < n; i++){
            sb.append(donations.get(i).toString()+"\r\n");
        }
        sb.append("-----------------------------\r\n");
        lists.add(sb.toString());
        return 0;
    }

    @Override
    public int query(List<Recipient> recipients,List<String> lists) {
        Map<String,Long> name2donation = new HashMap<>();
        Set<String> names = new HashSet<>();
        for (Recipient r:recipients) {
            String name = r.getName();
            names.add(name.replace("\\s",""));
            String postcode = r.getPostcode();
            String key= name+"-"+postcode;
            Long donation = name2donation.get(key);
            name2donation.put(key,(donation == null ? 0L : donation )+r.getDonation());

        }
        List<Recipient> resultRecipients = new ArrayList<>();
        name2donation.forEach((k,v) ->{
            String [] keys = k.split("-");
            Recipient recipient = new Recipient(keys[0],v,keys[1]);
            resultRecipients.add(recipient);
        });
        StringBuilder sb = new StringBuilder();
        sb.append("-----").append("query recipients ").append("------\r\n");
        ArrayList<String> sortNames = new ArrayList<>(names);
        Collections.sort(sortNames);
        for (String name : sortNames ) {
            Optional<Recipient> max = resultRecipients.stream().filter(o -> name.equals(o.getName())).collect(Collectors.toList()).stream().max(Comparator.comparingLong(Recipient::getDonation));
            Long maxDonation = max.get().getDonation();
            List<String> collect = resultRecipients.stream().filter(o -> o.getDonation().equals(maxDonation)).map(o -> o.getPostcode()).collect(Collectors.toList());
            sb.append(name).append(": ").append(maxDonation).append("; postcode ").append(list2String(collect,", ")).append("\r\n");
        }
        sb.append("-----------------------------\r\n");
        lists.add(sb.toString());
        return 0;
    }

    private String list2String(List<String> list, String separator){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    public int queryAdaptor(String name,Set<Donator> recorde,List<String> lists){
        String[] params = name.split("\\s");
        if (params.length <2 && !"recipients".equals(params[0])){
            return 0;
        }
        switch (params[0]){
            case "name":
                query(name.substring(4).trim(),recorde,lists);
                break;
            case "top":
                DonatorRecord dr = new DonatorRecord();
                List<Donation>  donations = dr.readFromInputFile(recorde);
                donations.sort(Comparator.comparingDouble(Donation::getAmount).reversed().thenComparing(Donation::getName));
                query(Integer.parseInt(params[1]),donations,lists);
                break;
            case "recipients":
                List<Recipient> list = new ArrayList<>();
                recorde.stream().filter(o -> o.getRecipients() != null && o.getRecipients().size() > 0).map(o -> list.addAll(o.getRecipients())).collect(Collectors.toList());
                query(list,lists);
                break;
            default:break;
        }
        return 1;
    }

    public void process(String line,Set<Donator> recorde,List<String> lists){
        if (line != null && !"".equals(line) && !Util.isValid(line)){
            System.out.println("非法指令： " + line);
        }
        if (line.startsWith("update")){
            update(line.substring(6).trim(),recorde,lists);
        }
        if (line.startsWith("delete")){
            delete(line.substring(6).trim(),recorde,lists);
        }
        if (line.startsWith("donate")){
            donate(line.substring(6).trim(),recorde,lists);
        }
        if (line.startsWith("query")){
            queryAdaptor(line.substring(5).trim(),recorde,lists);
        }
    }
}
