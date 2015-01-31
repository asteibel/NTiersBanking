package tp.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tp.ejb.utils.Paging;
import domain.model.Account;
import domain.model.Transfer;

@Stateless
@LocalBean
public class TransferDaoBean implements TransferDao{

	@PersistenceContext(unitName = "BankingPU")
	private EntityManager em;
	
	public TransferDaoBean(){
		super();
		System.out.println("creating TransferDaoBean");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transfer> getList() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("alltransfers").getResultList();
	}

	public Transfer find(Transfer t){
		return em.find(Transfer.class, t.getId());
	}
	
	@Override
	public Transfer add(Transfer t) {
		// TODO Auto-generated method stub
		if(find(t)==null){
			em.persist(t);
			return t;
		}
		else
			return null;
	}

	@Override
	public Transfer find(int id) {
		// TODO Auto-generated method stub
		return em.find(Transfer.class, id);
	}

	@Override
	public Transfer delete(Transfer t) {
		// TODO Auto-generated method stub
		t = find(t);
		Transfer pr = prior(t);
		if(pr==null)
			pr=next(t);
		em.remove(t);
		return pr;
	}

	@Override
	public Transfer first() {
		// TODO Auto-generated method stub
		List<Transfer> ts = getList();
		return ts.get(0);
	}

	@Override
	public Transfer last() {
		// TODO Auto-generated method stub
		List<Transfer> ts = getList();
		return ts.get(ts.size()-1);
	}

	@Override
	public Transfer prior(Transfer t) {
		// TODO Auto-generated method stub
		return Paging.prior(getList(), t);
	}

	@Override
	public Transfer next(Transfer t) {
		// TODO Auto-generated method stub
		return Paging.next(getList(), t);
	}

	@Override
	public Transfer clone(Transfer t) {
		// TODO Auto-generated method stub
		Transfer nvT = new Transfer(t.getAmount(), t.getFromAccount(), t.getToAccount());
		em.persist(nvT);
		return nvT;
	}

	@Override
	public Transfer create() {
		// TODO Auto-generated method stub
		Transfer nvT = new Transfer();
		em.persist(nvT);
		return nvT;
	}

	@Override
	public Transfer merge(Transfer t) {
		// TODO Auto-generated method stub
		if(find(t)!=null){
			em.merge(t);
			return t;
		}
		else
			return null;
	}

	@Override
	public Transfer createTransfer(double amount, Account fromAccount,
			Account toAccount) {
		// TODO Auto-generated method stub
		Transfer t = create();
		t.setAmount(amount);
		t.setFromAccount(fromAccount);
		t.setToAccount(toAccount);
		em.persist(t);
		return t;
	}

	@Override
	public Transfer setAmount(Transfer t, double amount) {
		// TODO Auto-generated method stub
		t.setAmount(amount);
		em.merge(t);
		return t;
	}

	@Override
	public Transfer setFromAccount(Transfer t, Account a) {
		// TODO Auto-generated method stub
		t.setFromAccount(a);
		em.merge(t);
		em.merge(a);
		return t;
	}

	@Override
	public Transfer setToAccount(Transfer t, Account a) {
		// TODO Auto-generated method stub
		t.setToAccount(a);
		em.merge(t);
		em.merge(a);
		return t;
	}

	@Override
	public void makeTransfer(Transfer t){
		Account fa = t.getFromAccount();
		Account ta = t.getToAccount();
		fa.setBalance(fa.getBalance()-t.getAmount());
		ta.setBalance(ta.getBalance()+t.getAmount());
		em.merge(fa);
		em.merge(ta);
	}
}
