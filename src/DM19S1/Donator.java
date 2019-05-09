package DM19S1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
class Donator {
    private int D_id;
    private String name;
    private String birthday;
    private String address;
    private String postcode;
    private String phone;
    private List<Recipient> recipients = new ArrayList<>();

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecipient(){
        StringBuilder builder = new StringBuilder();
        for (Recipient recipient: recipients) {
            builder.append(recipient.getName()).append(",");
        }
        String recipientName = builder.toString();
        if (recipientName != null && !"".equals(recipientName)){
            return  recipientName.substring(0,recipientName.length()-1);
        }
        return  "";
    }

    public String getDonation(){
        StringBuilder builder = new StringBuilder();
        for (Recipient recipient: recipients) {
            builder.append(recipient.getDonation()).append(",");
        }
        String donation = builder.toString();
        if (donation != null && !"".equals(donation)){
            return donation.substring(0, donation.length() - 1);
        }

        return  "";
    }
    public Donator() { }

    public Donator(String name, String birthday, String address, String postcode, String phone) {
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.postcode = postcode;
        this.phone = phone;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donator donator = (Donator) o;
        return Objects.equals(name, donator.name) &&
                Objects.equals(birthday, donator.birthday);
    }


    public int hashCode() {
        return Objects.hash(name, birthday);
    }

    public boolean isValidToAdd(){
        return validName() && validBirthday() && validAddress() && validPhone() && validPostcode();
    }
    public boolean isDelValidToAdd(){
        return validName() && validBirthday();
    }
    private boolean validName(){
        if (name != null && name.matches("[a-zA-Z\\s]+")) {
            return true;
        }else{
            return false;
        }
    }

    private boolean validBirthday(){
        boolean isValid = false;
        if (birthday == null){
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            format.parse(birthday);
            String [] dd = birthday.split("-");
            if (Integer.parseInt(dd[0]) >0 && Integer.parseInt(dd[0]) < 32 && Integer.parseInt(dd[1])>0 && Integer.parseInt(dd[1]) < 13){
                isValid = true;
            }
        } catch (ParseException e) {

        }
        return isValid;
    }
    private boolean validAddress(){
        Set<String>  set = new HashSet<>();
        set.add("NSW");
        set.add("QLD");
        set.add("SA");
        set.add("TAS");
        set.add("VIC");
        set.add("WA");
        set.add("ACT");
        set.add("NT");
        boolean isValid = false;
        if (address != null) {
            String [] strings = address.split(",");
            if (set.contains(strings[strings.length-1].trim().toUpperCase())) {
                isValid = true;
            }
        }else {
            isValid = true;
        }
        return isValid;
    }
    private boolean validPostcode(){
        if (postcode !=null ){
            if (postcode.matches("^[0-9]{4}$")){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
    private boolean validPhone(){
        if (phone !=null){
            if(phone.matches("^[0-9]{8}$")) {
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("name  ").append(name).append("\n")
                .append("birthday  ").append(birthday).append("\n");
        if (address != null && !"".equals(address)){
            builder.append("address  ").append(address).append("\n");
        }
        if (postcode!= null && !"".equals(postcode)){
            builder.append("postcode  ").append(postcode).append("\n");
        }
        if (phone != null && !"".equals(phone)){
            builder.append("phone  ").append(phone).append("\n");
        }
        if (!getDonation().equals("") ){
            builder.append("donation  ").append(getDonation()).append("\n");
        }
        if (!getRecipient().equals("")){
            builder.append("recipient  ").append(getRecipient()).append("\n");
        }
        builder.append("\r\n");
        return builder.toString();
    }

}