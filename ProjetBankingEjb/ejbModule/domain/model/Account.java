package domain.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
  @NamedQuery(name = "allaccounts", query = "select a FROM Account a"),
  @NamedQuery(name = "allaccountsbycustomer", query = "select a FROM Account a where a.owner = :owner")
})
public class Account implements Serializable {
	
	private static final long serialVersionUID = 6067819559697622964L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	private String accountNumber;
	
	/**
	 * Transfers from this account
	 */
	@OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Transfer> transfersFrom;
	/**
	 * Transfers to this account
	 */
	@OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Transfer> transfersTo;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Customer owner;
	
	/**
	 * Si c'est le compte d'une banque (banque ou caisse)
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bank")
	private Bank bank;
	
	private double balance;
	private double interestRate;
	private double overdraftPenalty;
	private double overdraftLimit;
	
	public Set<Transfer> getTransfersTo(){
		return transfersTo;
	}
	public void setTransfersTo(Set<Transfer> transfersTo){
		this.transfersTo=transfersTo;
	}
	public Set<Transfer> getTransfersFrom(){
		return transfersFrom;
	}
	public void setTransfersFrom(Set<Transfer> transfersFrom){
		this.transfersFrom=transfersFrom;
	}
	
	public void addTransferFrom(Transfer transferFrom){
		transfersFrom.add(transferFrom);
		transferFrom.setFromAccount(this);
	}
	public void addTransferTo(Transfer transferTo){
		transfersTo.add(transferTo);
		transferTo.setToAccount(this);
	}
	public void removeTransferFrom(Transfer transferFrom){
		transfersFrom.remove(transferFrom);
	}
	public void removeTransferTo(Transfer transferTo){
		transfersTo.remove(transferTo);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Customer getOwner() {
		return owner;
	}
	public void setOwner(Customer owner) {
		this.owner = owner;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getOverdraftPenalty() {
		return overdraftPenalty;
	}
	public void setOverdraftPenalty(double overdraftPenalty) {
		this.overdraftPenalty = overdraftPenalty;
	}
	public double getOverdraftLimit() {
		return overdraftLimit;
	}
	public void setOverdraftLimit(double overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	public Account(String accountNumber, Customer owner,
			double balance, double interestRate, double overdraftPenalty,
			double overdraftLimit) {
		super();
		this.accountNumber = accountNumber;
		this.owner = owner;
		this.balance = balance;
		this.interestRate = interestRate;
		this.overdraftPenalty = overdraftPenalty;
		this.overdraftLimit = overdraftLimit;
	}
	
	public Account() {
		super();
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNumber=" + accountNumber
				+ ", owner=" + owner + ", balance=" + balance
				+ ", interestRate=" + interestRate + ", overdraftPenalty="
				+ overdraftPenalty + ", overdraftLimit=" + overdraftLimit + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
