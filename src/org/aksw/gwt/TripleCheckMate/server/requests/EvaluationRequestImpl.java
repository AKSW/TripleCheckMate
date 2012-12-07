package org.aksw.gwt.TripleCheckMate.server.requests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.aksw.gwt.TripleCheckMate.client.requests.EvaluationRequest;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.EvaluateResource;
import org.aksw.gwt.TripleCheckMate.shared.storage.StorageFactory;
import org.aksw.gwt.TripleCheckMate.shared.storage.StorageService;
import org.aksw.gwt.TripleCheckMate.shared.storage.exception.StorageServiceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EvaluationRequestImpl extends RemoteServiceServlet implements
		EvaluationRequest {

	/**
	 * Auto-generated for serialization
	 */
	private static final long serialVersionUID = 8754071543241872823L;

	private String getAbsolutePath() {
		return getServletContext().getRealPath("/");
	}

	public String SaveEvaluation(long sessionID, EvaluateResource item) throws StorageServiceException {

		StorageService service = StorageFactory.create(getAbsolutePath());
		service.saveEvaluation(sessionID, item);
		return "OK";
	}

	@Override
	public List<String> getExistingEvaluations(long user, String classType)
			throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.getExistingEvaluations(user, classType);
	}

	@Override
	public List<ClassItem> getClassChildren(long classID)
			throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.getClassChildren(classID);
	}

	@Override
	public int saveClassCount(long id, long count)
			throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.updateClassCount(id, count);
	}

	@Override
	public int saveClassFromOWL(String url) throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.saveClasses(getCategoriesFromOWL(url));
	}

	@Override
	public List<ErrorItem> getErrorChildren(long errorID)
			throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.getErrorChildren(errorID);
	}

	/**
	 * Helper function to generate a list of ClassItem's to be inserted inthe
	 * StorageService To be used in combination with
	 * service.saveClasses(getCategoriesFromOWL(owlFileURL));
	 * 
	 * @param owlFileURL
	 *            the owl URL to retrieve the xml file
	 * @return a list of ClassItem's to beinserted in the StorageService
	 * @throws Throwable
	 */

	private List<ClassItem> getCategoriesFromOWL(String owlFileURL) {
		List<ClassItem> categories = new ArrayList<ClassItem>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;

			db = dbf.newDocumentBuilder();

			Document doc = (Document) db
					.parse(new URL(owlFileURL).openStream());
			// Document doc = db.parse("/home/jim/dbpedia.owl.rdf");
			doc.getDocumentElement().normalize();
			// xmlns:owl="http://www.w3.org/2002/07/owl#"
			// xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
			// <owl:Class
			// rdf:about="http://dbpedia.org/ontology/BasketballLeague">
			// <rdfs:subClassOf
			// rdf:resource="http://dbpedia.org/ontology/SportsLeague" />
			// </owl:Class>
			Element root = doc.getDocumentElement();
			NodeList nList = root.getElementsByTagName("owl:Class");
			if (nList.getLength() > 0) {

				categories.add(new ClassItem("owl:Thing",
						"http://www.w3.org/2002/07/owl#Thing", ""));

				for (int i = 0; i < nList.getLength(); i++) {
					Element e = (Element) nList.item(i);

					String uri = e.getAttribute("rdf:about");
					String name = uri.replaceFirst(
							"http://dbpedia.org/ontology/", "");
					String parenturi = "";

					NodeList subclass = e
							.getElementsByTagName("rdfs:subClassOf");
					if (subclass.getLength() > 0) {
						for (int j = 0; j < subclass.getLength(); j++) {
							Element elem = (Element) subclass.item(0);
							String tmp = elem.getAttribute("rdf:resource");
							if (tmp.startsWith("http://dbpedia.org/ontology")
									|| tmp.equals("http://www.w3.org/2002/07/owl#Thing"))
								parenturi = tmp;
						}
					}
					if (parenturi.equals("")) {
						parenturi = "http://www.w3.org/2002/07/owl#Thing";
					}
					categories.add(new ClassItem(name, uri, parenturi));
				}
			}
			// Fill parent id information
			int i = 0;
			HashMap<String, Long> parentCache = new HashMap<String, Long>();
			HashMap<String, Long> leafCache = new HashMap<String, Long>();
			for (ClassItem item : categories) {
				item.ID = i++;
				parentCache.put(item.uri, item.ID);

				if (leafCache.get(item.parent) == null)
					leafCache.put(item.parent, item.ID);
			}
			for (ClassItem item2 : categories) {
				Long pid = parentCache.get(item2.parent);
				item2.parentID = (pid == null) ? -1 : pid;
				item2.isLeaf = leafCache.get(item2.uri) == null;
			}

		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categories;
	}

}