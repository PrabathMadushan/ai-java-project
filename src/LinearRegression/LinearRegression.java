package LinearRegression;

import java.util.ArrayList;

public class LinearRegression {

    private ArrayList<DataModel> traingData;

    private float m;
    private float c;

    public LinearRegression(ArrayList<DataModel> traingData) {
        this.traingData = traingData;
    }

    public static void main(String[] args) {
        ArrayList<DataModel> l = new ArrayList<>();
        l.add(new DataModel(8, 3)  );
        l.add(new DataModel(2, 10) );
        l.add(new DataModel(11, 3) );
        l.add(new DataModel(6, 6) ) ;
        l.add(new DataModel(5, 8) );
        l.add(new DataModel(4, 12 ));
        l.add(new DataModel(12, 1) );
        l.add(new DataModel(9, 4)) ;
        l.add(new DataModel(6, 9)  ) ;
        l.add(new DataModel(1, 14) );
        LinearRegression lr = new LinearRegression(l);
        lr.genBestFitLine();

    }

    public void genBestFitLine() {
        float sum_X_ = getX_();
        float sum_Y_ = getY_();
        float sum_X_m_Xs = getSum_X_m_Xs(sum_X_);
        float sum_X_m_X_ml_Y_m_Y_ = getSum_X_m_X_ml_Y_m_Y_(sum_X_, sum_Y_);
        m = sum_X_m_X_ml_Y_m_Y_ / sum_X_m_Xs;
        c = sum_Y_ - (m * sum_X_);
        log("y = mx + c");
        log("m: " + m);
        log("c: " + c);
    }

    private float getSum_X_m_X_ml_Y_m_Y_(float sum_X_, float sum_Y_) {
        return traingData
                .stream()
                .map((d) -> ((d.getX() - sum_X_) * (d.getY() - sum_Y_)))
                .reduce((c, i) -> c + i)
                .get();
    }

    private float getSum_X_m_Xs(float sum_X_) {
        return traingData
                .stream()
                .map((dm) -> (dm.getX() - sum_X_) * (dm.getX() - sum_X_))
                .reduce((c, i) -> c + i)
                .get();
    }

    private float getX_() {
        return traingData
                .stream()
                .map((dm) -> dm.getX())
                .reduce((a, i) -> a + i)
                .get() / traingData.size();
    }

    private float getY_() {
        return traingData
                .stream()
                .map((dm) -> dm.getY())
                .reduce((a, i) -> a + i)
                .get() / traingData.size();
    }

    public float predict(float x) {
        log("y=" + (m * x) + c);
        return (m * x) + c;
    }

    private static void log(String log) {
        System.out.println("log: " + log);
    }
}
