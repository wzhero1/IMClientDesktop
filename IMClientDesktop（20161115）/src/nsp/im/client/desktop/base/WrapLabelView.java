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
    	//����Line Break�㷨������0ʱ���ܰ���ĸ����
    	if (axis == View.X_AXIS) {
    		return 0;
    	}
    	return super.getMinimumSpan(axis);
    }
}