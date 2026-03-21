package duvan_vargas.btg_backend.util;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


public interface ITools {

    static boolean isFechaAMayorIgualQueFechaB(Date aFechaA, Date aFechaB, String aTipoComparacion) {
        boolean ok = false;
        if (aFechaA != null && aFechaB != null) {
            if (aTipoComparacion.equals(">")) {
                if (getStringFecha(aFechaA).compareTo(getStringFecha(aFechaB)) > 0) {
                    ok = true;
                }
            } else if (aTipoComparacion.equals("=")) {
                if (getStringFecha(aFechaA).compareTo(getStringFecha(aFechaB)) == 0) {
                    ok = true;
                }
            } else if (getStringFecha(aFechaA).compareTo(getStringFecha(aFechaB)) >= 0) {
                ok = true;
            }
        }

        return ok;
    }

    static boolean isFechaAMenorIgualQueFechaB(Date aFechaA, Date aFechaB, String aTipoComparacion) {
        boolean ok = false;
        if (aFechaA != null && aFechaB != null) {
            if (aTipoComparacion.equals("<")) {
                if (getStringFecha(aFechaA).compareTo(getStringFecha(aFechaB)) < 0) {
                    ok = true;
                }
            } else if (aTipoComparacion.equals("=")) {
                if (getStringFecha(aFechaA).compareTo(getStringFecha(aFechaB)) == 0) {
                    ok = true;
                }
            } else if (getStringFecha(aFechaA).compareTo(getStringFecha(aFechaB)) <= 0) {
                ok = true;
            }
        }

        return ok;
    }

    static Pageable getPageRequest(Pageable pageable, Map<String, String> clavesToSort) {
        Sort sort = Sort.unsorted();

        Sort.Order s;
        for(Iterator var3 = pageable.getSort().iterator(); var3.hasNext(); sort = sort.and(Sort.by(s.getDirection(), new String[]{(String)clavesToSort.get(s.getProperty())}))) {
            s = (Sort.Order)var3.next();
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }



    static String getStringFecha(Date aFecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return aFecha != null ? formato.format(aFecha) : "";
    }

    String PATTERN_DATE = "yyyy-MM-dd";
    String PATTERN_DATE_FECHA_HORA_MINUTO = "yyyy-MM-dd HH:mm";
    String PATTERN_DATE_FECHA_HORA_MINUTO_SEGUNDO = "yyyy-MM-dd HH:mm:ss";
    String ZONA_HORARIA_BOGOTA= "America/Bogota";
}
