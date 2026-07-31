package com.vaczine.Game;


import java.util.ArrayList;


public class Calendario {


    ArrayList<String[]> edades,vacunas;

    //
    //
    // ponga aca el texto generado para las edades

    String[] e1= {"Recién nacido","BCG","Única dosis antes de egresar de maternidad.","Hepatitis B","Dosis neonatal en las primeras 12 horas de vida.","null","null","null","null","null","null","null","null","null","null"};
    String[] e2= {"2 meses","Neumococo conjugada 13 valente","Primera dosis.","Quíntuple o pentavalente","Primera dosis.","IPV","Primera dosis.","Rotavirus","Primera dosis.","null","null","null","null","null","null"};
    String[] e3= {"3 meses","Meningococo ACYW","Primera dosis.","null","null","null","null","null","null","null","null","null","null","null","null"};
    String[] e4= {"4 meses","Neumococo conjugada 13 valente","Segunda dosis","Quíntuple o pentavalente","Segunda dosis.","IPV","Segunda dosis.","Rotavirus","Segunda dosis.","null","null","null","null","null","null"};
    String[] e5= {"5 meses","Meningococo ACYW","Segunda dosis.","null","null","null","null","null","null","null","null","null","null","null","null"};
    String[] e6= {"6 meses","Quíntuple o pentavalente","Tercera dosis.","IPV","Tercera dosis.","Antigripal","Dosis anual. Si es la primera vacunación, deberán recibir dos dosis separadas por al menos por cuatro semanas.","null","null","null","null","null","null","null","null"};
    String[] e7= {"12 meses","Neumococo conjugada 13 valente","Refuerzo.","Antigripal","Dosis anual. Si es la primera vacunación, deberán recibir dos dosis separadas por al menos por cuatro semanas.","Hepatitis A","Única dosis.","Triple Viral","Primera dosis.","null","null","null","null","null","null","null","null"};
    String[] e8= {"15 meses","Meningococo ACYW","Refuerzo.","Antigripal","Dosis anual. Si es la primera vacunación, deberán recibir dos dosis separadas por al menos por cuatro semanas.","Varicela","Primera dosis.","null","null","null","null","null","null","null","null"};
    String[] e9= {"15-18 meses","Quíntuple o pentavalente","Refuerzo.","Antigripal","Dosis anual. Si es la primera vacunación, deberán recibir dos dosis separadas por al menos por cuatro semanas.","null","null","null","null","null","null","null","null","null","null"};
    String[] e10= {"18 meses","Antigripal","Dosis anual. Si es la primera vacunación, deberán recibir dos dosis separadas por al menos por cuatro semanas.","Fiebre Amarilla","Primera dosis. Para residentes en zona de riesgo.","null","null","null","null","null","null","null","null","null","null"};
    String[] e11= {"24 meses","Antigripal","Dosis anual. Si es la primera vacunación, deberán recibir dos dosis separadas por al menos por cuatro semanas.","null","null","null","null","null","null","null","null","null","null","null","null"};
    String[] e12= {"5 años","IPV","Primer refuerzo.","Antigripal","Dosis anual. Recomendada a personas con factores de riesgo y adultos mayores de 65 años.","Triple Viral","Segunda dosis.","Varicela","Segunda dosis.","Triple Bacteriana Celular","Segundo refuerzo.","null","null","null","null","null","null"};
    String[] e13= {"11 años","Hepatitis B","Iniciar o completar esquema. Vacunación universal. Si no hubiera recibido el esquema completo. En caso de tener que iniciar: aplicar primera dosis, segunda dosis al mes de la primera y tercera dosis a los seis meses de la primera.","Meningococo ACYW","Única dosis.","Antigripal","Dosis anual. Recomendada a personas con factores de riesgo y adultos mayores de 65 años.","Triple Viral","Iniciar o completar esquema. Si no hubiera recibido dos dosis de triple viral o una dosis de triple viral + una dosis de doble viral, después del año de vida para los nacidos después de 1965.","Triple Bacteriana Acelular","Refuerzo.","Virus Papiloma Humano","Dos dosis. Varones y mujeres deben recibir dos dosis separadas por un intervalo mínimo de 6 meses.","Fiebre Amarilla","Refuerzo. Residentes zona de riesgo. Único refuerzo a los 10 año de la primera dosis"};
    String[] e14= {"15 años o más","Hepatitis B","Iniciar o completar esquema. Vacunación universal. Si no hubiera recibido el esquema completo. En caso de tener que iniciar: aplicar primera dosis, segunda dosis al mes de la primera y tercera dosis a los seis meses de la primera.","Antigripal","Dosis anual. Recomendada a personas con factores de riesgo y adultos mayores de 65 años.","Triple Viral","Iniciar o completar esquema. Si no hubiera recibido dos dosis de triple viral o una dosis de triple viral + una dosis de doble viral, después del año de vida para los nacidos después de 1965. ","Fiebre Amarilla","Única dosis. Residentes y/o trabajadores con riesgo ocupacional en zona de riesgo y que no hayan recibido anteriormente la vacuna.","Fiebre Hemorrágica Argentina","Única dosis. Residentes y/o trabajadores con riesgo ocupacional en zona de riesgo y que no hayan recibido anteriormente la vacuna.","null","null","null","null"};
    String[] e15= {"Adultos","Hepatitis B","Iniciar o completar esquema. Vacunación universal. Si no hubiera recibido el esquema completo. En caso de tener que iniciar: aplicar primera dosis, segunda dosis al mes de la primera y tercera dosis a los seis meses de la primera.","Neumococo conjugada 13 valente","Esquema secuencial.","Antigripal","Dosis anual. Recomendada a personas con factores de riesgo y adultos mayores de 65 años.","Triple Viral","Iniciar o completar esquema. Si no hubiera recibido dos dosis de triple viral o una dosis de triple viral + una dosis de doble viral, después del año de vida para los nacidos después de 1965. ","Doble Bacteriana","Refuerzo cada 10 años.","Fiebre Amarilla","Única dosis. Residentes y/o trabajadores con riesgo ocupacional en zona de riesgo y que no hayan recibido anteriormente la vacuna.","Fiebre Hemorrágica Argentina","Única dosis. Residentes y/o trabajadores con riesgo ocupacional en zona de riesgo y que no hayan recibido anteriormente la vacuna."};
    String[] e16= {"Embarazadas","Hepatitis B","Iniciar o completar esquema. Vacunación universal. Si no hubiera recibido el esquema completo. En caso de tener que iniciar: aplicar primera dosis, segunda dosis al mes de la primera y tercera dosis a los seis meses de la primera.","Antigripal","Una dosis en cualquier trimestre de la gestación.","Triple Bacteriana Acelular","Una dosis. Aplicar en cada embarazo independientemente del tiempo transcurrido desde la dosis previa. Aplicar a partir de la semana 20.","null","null","null","null","null","null","null","null"};
    String[] e17= {"Puerperio","Hepatitis B","Iniciar o completar esquema. Vacunación universal. Si no hubiera recibido el esquema completo. En caso de tener que iniciar: aplicar primera dosis, segunda dosis al mes de la primera y tercera dosis a los seis meses de la primera.","Antigripal","Una dosis si no la recibieron durante el embarazo.","Triple Viral","Iniciar o completar esquema. Si no hubiera recibido dos dosis de triple viral o una dosis de triple viral + una dosis de doble viral, después del año de vida para los nacidos después de 1965. ","null","null","null","null","null","null","null","null"};
    String[] e18= {"Personal de salud","Hepatitis B","Iniciar o completar esquema. Vacunación universal. Si no hubiera recibido el esquema completo. En caso de tener que iniciar: aplicar primera dosis, segunda dosis al mes de la primera y tercera dosis a los seis meses de la primera.","Antigripal","Dosis anual.","Triple Viral","Iniciar o completar esquema. Si no hubiera recibido dos dosis de triple viral o una dosis de triple viral + una dosis de doble viral, después del año de vida para los nacidos después de 1965. ","Triple Bacteriana Acelular","Una dosis a personal de salud que asiste a menores de 12 meses. Revacunar cada 5 años.","null","null","null","null","null","null"};

    //ponga aca el texto generado para las vavunas

    String[] v1={"BCG","Bacillus de Calmette y Guérin. Protege contra la tuberculosis causada por el Mycobacterium tuberculosis","Edad de vacunación: Recién nacido (dosis única)."};
    String[] v2={"Hepatitis B","Protege contra la la hepatitis causada por el virus de la hepatitis B.","Edad de vacunación: Recién nacido (dosis neonatal), 11 años en adelante (iniciar o completar esquema)."};
    String[] v3={"Neumococo conjugada 13 valente","Protege contra 13 tipos de bacterias neumocócicas causantes de: meningitis neumocócica, neumonía, septicemia y otitis.","Edad de vacunación: 2 meses (1ra dosis), 4 meses (2da dosis), 12 meses (refuerso), Adultos (esquema secuencial)."};
    String[] v4={"Quíntuple o pentavalente","DTP-HB. Protege contra la difteria, el tétanos, la tos ferina, la hepatitis B y Haemophilus influenzae de tipo B (Hib) que causa neumonía y meningitis.","Edad de vacunación: 2 meses (1ra dosis), 4 meses (2da dosis), 6 meses (3ra dosis), 15-18 meses (1re refuerso)."};
    String[] v5={"IPV","La Inactivated Polio Vaccine (IPV) (vacuna inactivada contra la polio). Protege contra la poliomielitis causada por el poliovirus. ","Edad de vacunación: 2 meses (1ra dosis), 4 meses (2da dosis), 6 meses (3ra dosis), 5 años (refuerzo)."};
    String[] v6={"Rotavirus","Protege contra la gastroenteritis causada por rotavirus.","Edad de vacunación: 2 meses (1ra dosis), 4 meses (2da dosis)."};
    String[] v7={"Meningococo ACYW","Protege contre la meningitis bacteriana y a otras infecciones graves causadas por la bacteria meningococo.","Edad de vacunación: 3 meses (1ra dosis), 5 meses (2da dosis), 15 meses (refuerso), 11 años (única dosis)."};
    String[] v8={"Antigripal","Protege contra la Gripe o Influenza causada por virus de Influenza A y B.","Edad de vacunación: 6 meses en adelante (dosis anual)."};
    String[] v9={"Hepatitis A","Protege contra la la hepatitis causada por el virus de la hepatitis A.","Edad de vacunación: 12 meses (única dosis)."};
    String[] v10={"Triple Viral","Protege contra el sarampión, rubéola y paperas.","Edad de vacunación: 12 meses (1ra dosis), 5 años (2da dosis), 11 en adelante (iniciar o completar esquema), puerperio y personal de salud (iniciar o completar esquema)."};
    String[] v11={"Varicela","Protege contra la varicela causada por el virus varicela-zóster","Edad de vacunación: 15 meses (primera dosis), 5 años (segunda dosis)."};
    String[] v12={"Triple Bacteriana Celular","DTP. Protege contra la difteria, el tétanos y la tos convulsa.","Edad de vacunación: 5 años (2do refuerso)."};
    String[] v13={"Triple Bacteriana Acelular","dTpa. Protege contra la difteria, el tétanos y la tos convulsa.","Edad de vacunación: 2 meses (1ra dosis), 4 meses (2da dosis)."};
    String[] v14={"Virus Papiloma Humano","Protege contra cáncer cervical y verrugas genitales causada por la ciertas cepas del virus de papiloma humano (VPH). ","Edad de vacunación: 11 años (2 dosis)."};
    String[] v15={"Doble Bacteriana","dT. Protege contra la difteria y el tétanos.","Edad de vacunación: Adultos (cada diez años)."};
    String[] v16={"Fiebre Amarilla","Protege contra la fiebre amarilla causada por el virus de la fiebre amarilla del género Flavivirus.","Edad de vacunación: 18 meses (1ra dosis), 11 años (refuerzo), 15 años (única dosis), Adultos (única dosis). Eclusivo zona de riesgo."};
    String[] v17={"Fiebre Hemorrágica Argentina","Protege contra la Fiebre Hemorrágica Argentina (FHA) causada por el virus Junín.","Edad de vacunación: 15 años (única dosis), Adultos (única dosis). Eclusivo zona de riesgo."};
    //////



   public Calendario(){

        edades = new ArrayList<>();
        edades.add(e1);
        edades.add(e2);
        edades.add(e3);
        edades.add(e4);
        edades.add(e5);
        edades.add(e6);
        edades.add(e7);
        edades.add(e8);
        edades.add(e9);
        edades.add(e10);
        edades.add(e11);
        edades.add(e12);
        edades.add(e13);
        edades.add(e14);
        edades.add(e15);
        edades.add(e16);
        edades.add(e17);
        edades.add(e18);


        vacunas = new ArrayList<>();
        vacunas.add(v1);
       vacunas.add(v2);
       vacunas.add(v3);
       vacunas.add(v4);
       vacunas.add(v5);
       vacunas.add(v6);
       vacunas.add(v7);
       vacunas.add(v8);
       vacunas.add(v9);
       vacunas.add(v10);
       vacunas.add(v11);
       vacunas.add(v12);
       vacunas.add(v13);
       vacunas.add(v14);
       vacunas.add(v15);
       vacunas.add(v16);
       vacunas.add(v17);

    }

    public ArrayList<String[]> getEdades() {
        return edades;
    }

    public ArrayList<String[]> getVacunas() {
        return vacunas;
    }
}
