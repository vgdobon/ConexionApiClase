public class Checker {

    public boolean checkCoordinates(String[] coordenadas){
        boolean isChecked = false;

        if(coordenadas.length==2){
            for (int i = 0; i < coordenadas.length; i++) {

                try {
                    float numeroFlotante= Float.parseFloat(coordenadas[i]);
                    isChecked=true;
                }catch (NullPointerException e ){
                    e.printStackTrace();
                    isChecked=false;
                }catch(NumberFormatException e){
                    e.printStackTrace();
                    isChecked=false;
                }
            }
        }

        return isChecked;
    }
}
