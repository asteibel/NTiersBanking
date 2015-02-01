package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import tp.ejb.AccountDaoBean;
import tp.ejb.TransferDao;
import domain.model.Account;
import domain.model.Customer;
import domain.model.Transfer;

@ManagedBean(name="accountWeb")
@SessionScoped
public class AccountWeb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AccountDaoBean accountDao;
	
	@EJB
	private TransferDao transferDao;
	
	private int customerId;
	
	private int accountId;
	
	public int getAccountId(){
		return accountId;
	}
	
	public int getCustomerId(){
		return customerId;
	}
	public void setCustomerId(int customerId){
		Customer c = new Customer();
		c.setId(customerId);
		customerWeb.setCurrentCustomer(c);
		this.customerId=customerId;
	}
	
	private Account currentAccount;
	
	@ManagedProperty(value ="#{customerWeb}")
	private CustomerWeb customerWeb;
	
	public CustomerWeb getCustomerWeb() {
		return customerWeb;
	}
	public void setCustomerWeb(CustomerWeb customerWeb) {
		this.customerWeb = customerWeb;
	}
	
	public List<Account> getAllAccounts(){
		Customer c = customerWeb.getCurrentCustomer();
		List<Account> accounts = accountDao.getList(c);
		int siz = accounts.size();
		if (siz>0 && (currentAccount == null || !currentAccount.getOwner().equals(c)))
			currentAccount= accounts.get(0);
		return accounts;
	}
	
	

	public void modify() {
		accountDao.merge(customerWeb.getCurrentCustomer(), currentAccount);
		MBUtils.redirect("allaccountss.xhtml?id=" + customerId);
	}

	public void next() {
		setCurrentAccount(accountDao.next(customerWeb.getCurrentCustomer(), getCurrentAccount()));
		
		MBUtils.redirect("allaccounts.xhtml?id=" + customerId);
	}

	public void prior() {
		setCurrentAccount(accountDao.prior(customerWeb.getCurrentCustomer(), getCurrentAccount()));
		
		MBUtils.redirect("allaccounts.xhtml?id=" + customerId);
	}
	
	public Account getCurrentAccount() {
		if (currentAccount == null
				|| !currentAccount.getOwner().equals(customerWeb.getCurrentCustomer()))
			getAllAccounts();
		return currentAccount;
	}
	public void setCurrentAccount(Account currentAccount){
		this.currentAccount=currentAccount;
	}
	
	public void transfer(){
		MBUtils.redirect("transfer.xhtml?id="+customerId);
	}
	public void transfert(){
		Transfer t = transferDao.createTransfer(montant, transferFrom, transferTo);
		transferDao.makeTransfer(t);
	}
	private Account transferFrom;
	private Account transferTo;
	public void setTransferFrom(Account transferFrom){
		this.transferFrom=transferFrom;
	}
	public Account getTransferFrom(){
		return transferFrom;
	}
	public void setTransferTo(Account transferTo){
		this.transferTo=transferTo;
	}
	public Account getTransferTo(){
		return transferTo;
	}
	
	private double montant;
	public void setMontant(double montant){
		this.montant=montant;
	}
	public double getMontant(){
		return montant;
	}
	public void retrait(){
		accountDao.withdrawAccount(currentAccount, montant);
		MBUtils.redirect("allaccounts.xhtml?id"+customerId);
	}
	public void depot(){
		accountDao.addToAccount(currentAccount, montant);
		MBUtils.redirect("allaccounts.xhtml?id"+customerId);
	}
	public void retraitPage(int accountId){
		MBUtils.redirect("retrait.xhtml?id="+accountId);
	}
	public void depotPage(int accountId){
		MBUtils.redirect("depot.xhtml?id="+accountId);
	}
	
	
}
