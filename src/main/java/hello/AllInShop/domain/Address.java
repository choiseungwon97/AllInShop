package hello.AllInShop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address{

    private String city;
    private String street;
    private int zipcode;

    protected Address() {
    }

    public Address(String city, String street, int zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
