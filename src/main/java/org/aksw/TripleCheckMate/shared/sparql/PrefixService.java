/*******************************************************************************
 * Copyright 2013 Agile Knowledge Engineering and Semantic Web (AKSW) Group
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.aksw.TripleCheckMate.shared.sparql;

import java.util.HashMap;

public class PrefixService {
	private static String prefixes[][] = null; 
	private static HashMap<String, String> cache = new HashMap<String, String>();
	private PrefixService(){
		setupPrefixes();
	}
	
	public static String getAbbeviatedForm(String uri) {
		if (prefixes == null)
			setupPrefixes();
		String cacheHit = cache.get(uri);
		if (cacheHit != null)
			return cacheHit;
		
		for (int i=0; i< prefixes.length; i++){
			if (uri.startsWith(prefixes[i][1])){
				String abbreviated = uri.replaceFirst(prefixes[i][1], prefixes[i][0] + ":");
				if (i>1) // ommit resource & property 
					cache.put(uri,  abbreviated);
				return abbreviated;
			}
		}
		return uri;
	}
	
	private static void setupPrefixes(){
		
		String tmpPrefixes[][] = {
				{"dbpedia","http://dbpedia.org/resource/"},
				{"dbp-prop","http://dbpedia.org/property/"},
				{"dbp-owl","http://dbpedia.org/ontology/"},
				{"rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#"},
				{"yago","http://dbpedia.org/class/yago/"},
				{"foaf","http://xmlns.com/foaf/0.1/"},
				{"owl","http://www.w3.org/2002/07/owl#"},
				{"dc","http://purl.org/dc/elements/1.1/"},
				{"rdfs","http://www.w3.org/2000/01/rdf-schema#"},
				{"rss","http://purl.org/rss/1.0/"},
				{"sc","http://umbel.org/umbel/sc/"},
				{"fb","http://rdf.freebase.com/ns/"},
				{"geonames","http://www.geonames.org/ontology#"},
				{"skos","http://www.w3.org/2004/02/skos/core#"},
				{"geo","http://www.w3.org/2003/01/geo/wgs84_pos#"},
				{"sioc","http://rdfs.org/sioc/ns#"},
				{"cyc","http://sw.opencyc.org/concept/"},
				{"akt","http://www.aktors.org/ontology/portal#"},
				{"gr","http://purl.org/goodrelations/v1#"},
				{"xsd","http://www.w3.org/2001/XMLSchema#"},
				{"admin","http://webns.net/mvcb/"},
				{"dcterms","http://purl.org/dc/terms/"},
				{"swrc","http://swrc.ontoware.org/ontology#"},
				{"commerce","http://search.yahoo.com/searchmonkey/commerce/"},
				{"dbpprop","http://dbpedia.org/property/"},
				{"dct","http://purl.org/dc/terms/"},
				{"content","http://purl.org/rss/1.0/modules/content/"},
				{"xhtml","http://www.w3.org/1999/xhtml#"},
				{"doap","http://usefulinc.com/ns/doap#"},
				{"gldp","http://www.w3.org/ns/people#"},
				{"vcard","http://www.w3.org/2006/vcard/ns#"},
				{"dc11","http://purl.org/dc/elements/1.1/"},
				{"void","http://rdfs.org/ns/void#"},
				{"gen","http://www.w3.org/2006/gen/ont#"},
				{"bill","http://www.rdfabout.com/rdf/schema/usbill/"},
				{"org","http://www.w3.org/ns/org#"},
				{"wot","http://xmlns.com/wot/0.1/"},
				{"test2","http://this.invalid/test2#"},
				{"nie","http://www.semanticdesktop.org/ontologies/2007/01/19/nie#"},
				{"d2rq","http://www.wiwiss.fu-berlin.de/suhl/bizer/D2RQ/0.1#"},
				{"qb","http://purl.org/linked-data/cube#"},
				{"og","http://opengraphprotocol.org/schema/"},
				{"drugbank","http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/"},
				{"rel","http://purl.org/vocab/relationship/"},
				{"http","http://www.w3.org/2006/http#"},
				{"factbook","http://www4.wiwiss.fu-berlin.de/factbook/ns#"},
				{"vann","http://purl.org/vocab/vann/"},
				{"mo","http://purl.org/ontology/mo/"},
				{"dcmit","http://purl.org/dc/dcmitype/"},
				{"bibo","http://purl.org/ontology/bibo/"}
		};
		prefixes = new String[tmpPrefixes.length][2];
		for (int i=0; i<tmpPrefixes.length; i++){
			prefixes[i][0] = tmpPrefixes[i][0];
			prefixes[i][1] = tmpPrefixes[i][1];
		}
	}
}

/*


 * */
