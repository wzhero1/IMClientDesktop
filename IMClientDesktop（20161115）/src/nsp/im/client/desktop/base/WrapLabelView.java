package nsp.im.client.desktop.base;

import javax.swing.text.Element;
import javax.swing.text.LabelView;
import javax.swing.text.View;

public class WrapLabelView extends LabelView {
    public WrapLabelView(Element elem) {
        super(elem);
    }
    
    @Override
    public float getMinimumSpan(int axis) {
    	//根据Line Break算法，返回0时才能按字母断行
    	if (axis == View.X_AXIS) {
    		return 0;
    	}
    	return super.getMinimumSpan(axis);
    }
}