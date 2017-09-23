package br.com.devcase.boot.dwftaglibs.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.core.OrderComparator;

import br.com.devcase.boot.dwftaglibs.tag.NeedJavascriptTag.AddedJavascript;

public class PrintNeededJavascriptTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		Writer out = this.getJspContext().getOut();
		
		Object l = getJspContext().findAttribute(NeedJavascriptTag.NEEDED_JAVASCRIPT_ATTRIBUTE_NAME);
		if(l != null && l instanceof Collection<?>) {
			List<AddedJavascript> addedJavascripts = new ArrayList<>((Collection<AddedJavascript>) l);
			
			addedJavascripts.sort(OrderComparator.INSTANCE);
			
			for (AddedJavascript addedJavascript : addedJavascripts) {
				addedJavascript.writeScript(out);
			}
		}
	}

	

}
