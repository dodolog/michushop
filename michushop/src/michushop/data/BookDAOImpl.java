package michushop.data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Query;
import org.hibernate.type.StandardBasicTypes;

public class BookDAOImpl extends GenericDAOImpl<Book, BigDecimal> implements
		BookDAO {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public void addBook(String title, String description, String price,
			String pubDate) {

		Book book = new Book();
		book.setTitle(title);
		book.setDescription(description);
		book.setPrice(new BigDecimal(price));
		try {
			book.setPubDate(dateFormat.parse(pubDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		HibernateUtil.getSession().saveOrUpdate(book);

	}

	public void updateBook(String id, String title, String description,
			String price, String pubDate) {

		String sql = "UPDATE Book p SET title= :title , description= :description , price= :price , pubDate= :pubDate WHERE p.id = :id ";
		Query query = HibernateUtil.getSession().createQuery(sql);

		query.setParameter("id", new BigDecimal(id),
				StandardBasicTypes.BIG_DECIMAL);
		query.setParameter("title", title);
		query.setParameter("description", description);
		query.setParameter("price", new BigDecimal(price),
				StandardBasicTypes.BIG_DECIMAL);
		try {
			query.setParameter("pubDate", dateFormat.parse(pubDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		query.executeUpdate();
	}

}
