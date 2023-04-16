package brunner.client;

import javax.swing.tree.DefaultMutableTreeNode;

public class JBrunnerTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	String id;

	public JBrunnerTreeNode(String text, String id) {
		super(text);
		this.id = id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

}
