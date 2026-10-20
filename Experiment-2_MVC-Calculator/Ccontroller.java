import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ccontroller implements ActionListener {

    Cmodels cm;
    Cviews cv;

    Ccontroller(Cmodels cm, Cviews cv) {
        this.cm = cm;
        this.cv = cv;

        cv.add.addActionListener(this);
        cv.sub.addActionListener(this);
        cv.mul.addActionListener(this);
        cv.div.addActionListener(this);
        cv.per.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        double a = Double.parseDouble(cv.ta.getText());
        double b = Double.parseDouble(cv.tb.getText());
        double r = 0;

        if (e.getSource() == cv.add) {
            r = cm.add(a, b);
        } else if (e.getSource() == cv.sub) {
            r = cm.sub(a, b);
        } else if (e.getSource() == cv.mul) {
            r = cm.mul(a, b);
        } else if (e.getSource() == cv.div) {
            r = cm.div(a, b);
        }


        else if (e.getSource() == cv.per) {
            r = cm.per(a, b);
        }

        cv.tresult.setText(String.valueOf(r));
    }



}