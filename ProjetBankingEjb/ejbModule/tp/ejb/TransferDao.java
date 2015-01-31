package tp.ejb;

import java.util.List;

import javax.ejb.Remote;

import domain.model.Account;
import domain.model.Transfer;

@Remote
public interface TransferDao {

	public List<Transfer> getList();
	public Transfer add(Transfer t);
	public Transfer find(int id);
	public Transfer delete(Transfer t);
	public Transfer first();
	public Transfer last();
	public Transfer prior(Transfer t);
	public Transfer next(Transfer t);
	public Transfer clone(Transfer t);
	public Transfer create();
	public Transfer merge(Transfer t);
	
	Transfer createTransfer(double amount, Account fromAccount, Account toAccount);
	Transfer setAmount(Transfer t, double amount);
	Transfer setFromAccount(Transfer t, Account a);
	Transfer setToAccount(Transfer t,Account a);
	
	void makeTransfer(Transfer t);
	
}
