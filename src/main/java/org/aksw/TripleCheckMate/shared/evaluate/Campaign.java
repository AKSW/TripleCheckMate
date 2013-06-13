package org.aksw.TripleCheckMate.shared.evaluate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Campaign implements Comparable<Campaign>, Serializable {

    /**
     * Auto-generated for serialization
	 */
	private static final long serialVersionUID = -5029455682005412205L;
	
	public long campaignID = 0;
    public String name = "";
    public String endpoint = "";
    List<String> graphs  = new ArrayList<String>();

    public Campaign(){}

    public Campaign(long id, String name, String endpoint, String graphs) {
        this(id, name, endpoint, Arrays.asList( graphs.split(";") ));
    }

    public Campaign(long id, String name, String endpoint, List<String> graphs) {
        this.campaignID = id;
        this.name = name;
        this.endpoint = endpoint;
        this.graphs = graphs;
    }

    public int compareTo(Campaign campaign) {
        Long t = new Long(campaignID);
        Long c = new Long(campaign.campaignID);
        return t.compareTo(c);
    }
}
