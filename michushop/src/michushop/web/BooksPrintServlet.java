package michushop.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import michushop.InputStreamXML;
import michushop.data.Book;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

@WebServlet("/print")
public class BooksPrintServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FopFactory fopFactory = FopFactory.newInstance();
	private TransformerFactory tFactory = TransformerFactory.newInstance();

	public void init() throws ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		
		String select[] = request.getParameterValues("wybor");
		
		// Setup a buffer to obtain the content length
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// Setup FOP
		Fop fop = null;
		try {
			fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
		} catch (FOPException e) {
			e.printStackTrace();
		}
		
		// FIXME:
		File file2 = new File("/home/mich/dev/resin-pro-4.0.33/webapps/michushop/WEB-INF/pdf/foo-xml2fo.xsl");
				
		// Setup Transformer
		Source xsltSrc = new StreamSource(file2);
				
		Transformer transformer_ = null;
		try {
			transformer_ = tFactory.newTransformer(xsltSrc);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}

		// Make sure the XSL transformation's result is piped through to FOP
		Result res = null;
		try {
			res = new SAXResult(fop.getDefaultHandler());
		} catch (FOPException e1) {
			e1.printStackTrace();
		}
		
		// create and set object for source
		InputStreamXML<Book> streamBook = new InputStreamXML<Book>(Book.class);
		streamBook.init();
		Source src = new StreamSource( streamBook.getInputStreamXML(select)  );

		// Start the transformation and rendering process
		try {
			transformer_.transform(src, res);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		// Prepare response
		response.setContentType("application/pdf");
		response.setContentLength(out.size());

		// Send content to Browser
		try {
			response.getOutputStream().write(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
