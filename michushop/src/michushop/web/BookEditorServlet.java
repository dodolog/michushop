package michushop.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import michushop.data.Book;
import michushop.data.BookDAOImpl;
import michushop.data.HibernateUtil;

/**
 * Servlet implementation class BookEditorServlet
 */
@WebServlet("/book")
public class BookEditorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookEditorServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Inject
	private BookDAOImpl bookRepo;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	/** Prepare the book form before we display it. */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");

		try {
			// Begin unit of work
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();

			if (id != null && !id.isEmpty()) {
				BigDecimal bigId = new BigDecimal(id);
				Book book = bookRepo.findByID(Book.class, bigId);

				Date d = new Date(0);
				request.setAttribute("book", book);
				try {
					request.setAttribute("bookPubDate",
							book.getPubDate() == null ? dateFormat.format(d)
									: dateFormat.format(book.getPubDate()));
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

			// End unit of work
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
		} catch (Exception ex) {

			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();

			if (ServletException.class.isInstance(ex)) {
				throw (ServletException) ex;
			} else {
				throw new ServletException(ex);
			}
		}

		/* Redirect to book-form. */
		request.getSession().getServletContext()
				.getRequestDispatcher("/WEB-INF/pages/book-form.jsp")
				.forward(request, response);

	}

	/**
	 * Handles posting an HTML book form. 
	 * If the id is null then it is an /add book/, 
	 * if the id is set then it is an /update book/.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String price = request.getParameter("price");
		String pubDate = request.getParameter("pubDate");

		try {
			// Begin unit of work
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();

			String id = request.getParameter("id");
			if (id == null || id.isEmpty()) {
				bookRepo.addBook(title, description, price, pubDate);
			} else {
				bookRepo.updateBook(id, title, description, price, pubDate);
			}

			// End unit of work
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
		} catch (Exception ex) {

			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();

			if (ServletException.class.isInstance(ex)) {
				throw (ServletException) ex;
			} else {
				throw new ServletException(ex);
			}
		}

		response.sendRedirect(request.getContextPath() + "/book/");
	}
}