package domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	  @NamedQuery(name = "alltransfers", query = "select t FROM Transfer t"),
	})
public class Transfer implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
	private Date date;
	private double amount;
	
	@ManyToOne
	@JoinColumn(name="fromAccount")
	private Account fromAccount;
	@ManyToOne
	@JoinColumn(name="toAccount")
	private Account toAccount;
	
	
	public Transfer(){
		super();
	}
	
	public Transfer(double amount, Account fromAccount, Account toAccount) {
		super();
		this.amount = amount;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Account getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Account getToAccount() {
		return toAccount;
	}
	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}
	
	public String toString(){
		return "Transfer from "+fromAccount.toString()+" to "+toAccount.toString()+"of "+amount;
	}
	

}
