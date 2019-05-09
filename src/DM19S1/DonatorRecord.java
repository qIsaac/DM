package DM19S1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<Donation> readFromInputFile(Set<Donator> donators){
        List<Donation> donations =  donators.stream().filter(obj -> obj.getRecipients() != null && obj.getRecipients().size() > 0 ).map(b -> getDonation(b)).collect(Collectors.toList());
        return donations;
    }
    private Donation getDonation(Donator donator){
        Donation donation = new Donation();
        donation.setName(donator.getName());
        donation.setBirthday(donator.getBirthday());
        Double total = 0d;
        for (Recipient r:donator.getRecipients()) {
            total += Double.parseDouble(r.getDonation());
        }
        donation.setAmount(total);
        return donation;
    }
}
