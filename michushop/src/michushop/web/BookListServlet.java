package michushop.web;

import java.io.IOException;

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
 * Servlet implementation class BookListServlet
 */
@WebServlet("/book/")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookListServlet() {
		super();
	}

	@Inject
	private BookDAOImpl bookRepo;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			// Begin unit of work
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

			request.setAttribute("books", bookRepo.findAll(Book.class));

			// End unit of work
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
		} catch (Exception ex) {
			
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			
			if (ServletException.class.isInstance(ex)) {
				throw (ServletException) ex;
			} else {
				throw new ServletException(ex);
			}
		}
			request.getSession().getServletContext()
					.getRequestDispatcher("/WEB-INF/pages/book-list.jsp")
					.forward(request, response);
		
	}
}