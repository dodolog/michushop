package michushop;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import michushop.data.Book;
import michushop.data.BookDAOImpl;
import michushop.data.GenericDAOImpl;
import michushop.data.HibernateUtil;

public class InputStreamXML<T> {

	final private Class<T> dbClass;
	private GenericDAOImpl<T, BigDecimal> db;

	/**
	 *	 
	 */
	public InputStreamXML(Class<T> typeParameterClass) {
		this.dbClass = typeParameterClass;
	}

	public void init() {
		if (dbClass.equals(Book.class))
			setDb((GenericDAOImpl<T, BigDecimal>) new BookDAOImpl());
		else
			setDb(null);
	}

	public InputStream getInputStreamXML(String[] choices) {

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<basket>");
		try {
			// Begin unit of work
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();

			Book b = null;
			for (String id : choices) {
				sb.append("<book>");
				b = (Book) db.findByID(Book.class, new BigDecimal(id));

				sb.append("<title>");
				sb.append(b.getTitle());
				sb.append("</title>");
				sb.append("<description>");
				sb.append(b.getDescription());
				sb.append("</description>");
				sb.append("<price>");
				sb.append(b.getPrice());
				sb.append("</price>");
				sb.append("</book>");
			}
			// Begin unit of work
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
		} catch (Exception e) {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();
		}

		sb.append("</basket>");

		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
		return is;
	}

	public GenericDAOImpl<T, BigDecimal> getDb() {
		return this.db;
	}

	public void setDb(GenericDAOImpl<T, BigDecimal> d) {
		this.db = d;
	}

}
