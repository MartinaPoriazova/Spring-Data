package _05.entities;

import javax.persistence.*;

@Entity(name = "_05_bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "SWIFT_code", nullable = false)
    private String swiftCode;

    @ManyToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "credit_card_id",nullable = false)
    private CreditCard creditCard;

    public BankAccount() {
    }

    public BankAccount(String bankName, String swiftCode, User user, CreditCard creditCard) {
        this.bankName = bankName;
        this.swiftCode = swiftCode;
        this.user = user;
        this.creditCard = creditCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
