package DM19S1;

import java.util.Objects;

class Recipient {
    private int R_id;
    private String name;
    private Long donation;
    private String postcode;

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDonation() {
        return donation;
    }

    public void setDonation(Long donation) {
        this.donation = donation;
    }

    public Recipient(String name, Long donation,String postcode) {
        this.name = name;
        this.donation = donation;
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return Objects.equals(name, recipient.name) &&
                Objects.equals(donation, recipient.donation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, donation);
    }
}