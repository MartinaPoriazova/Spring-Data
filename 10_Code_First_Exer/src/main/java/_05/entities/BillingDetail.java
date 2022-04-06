package _05.entities;

import javax.persistence.*;

@Entity(name = "_05_billing_details")
public class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String number;

    @ManyToOne
    private User owner;

    @OneToOne
    @JoinColumn(name = "bank_account_id",referencedColumnName = "id")
    private BankAccount bankAccount;

    @OneToOne
    @JoinColumn(name = "credit_card_id",referencedColumnName = "id")
    private CreditCard creditCard;

    public BillingDetail() {
    }

    public BillingDetail(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
