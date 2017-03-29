/*
 *  excel2csv  xls/xlsx/csv/tsv converter
 *  Copyright (C) 2015 Yasunobu OKAMURA
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package info.informationsea.java.excel2csv;

import info.informationsea.tableio.TableWriter;
import info.informationsea.tableio.impl.AbstractTableWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor @Slf4j
public class FilteredWriter extends AbstractTableWriter {

    final private TableWriter writer;
    final private boolean autoConvert;
    final private boolean fistCount;
    int count=0;

    @Override
    public void printRecord(Object... values) throws Exception {
    	count++;
    	
        Object[] filteredValues = new Object[values.length + (fistCount?1:0)];
        if(fistCount){
    		filteredValues[0] = "\\N";
        }
        
        for (int i = 0; i < values.length; i++) {
        	int j = i;
        	if(fistCount){
        		j += 1;
        	}
        	
            if (autoConvert) {
                try {
                    filteredValues[j] = Double.valueOf(values[i].toString());
                } catch (NumberFormatException e) {
                    filteredValues[j] = values[i];
                }
            } else {
                if (values[i] != null)
                    filteredValues[j] = values[i].toString();
                else
                    filteredValues[j] = "";
            }
        }
        writer.printRecord(filteredValues);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}
