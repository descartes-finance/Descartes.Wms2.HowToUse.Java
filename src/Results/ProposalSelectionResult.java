package Results;

import java.util.ArrayList;
import java.util.List;

public class ProposalSelectionResult {
	public List<Integer> ResponseIds;
	public long ProposalId;
	
	public ProposalSelectionResult(long proposalId, ArrayList<Integer> responseIds) {
		this.ProposalId = proposalId;
		this.ResponseIds = responseIds;
	}
}
