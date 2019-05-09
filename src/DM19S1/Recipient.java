package DM19S1;

class Recipient {
    private int R_id;
    private String name;
    private String donation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDonation() {
        return donation;
    }

    public void setDonation(String donation) {
        this.donation = donation;
    }

    public Recipient(String name, String donation) {
        this.name = name;
        this.donation = donation;
    }
}