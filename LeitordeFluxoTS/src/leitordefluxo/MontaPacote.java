/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitordefluxo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodolfo
 */
public class MontaPacote {
    
    Transport_Packet ts;
    
    public static int binToDec(String bin) {
        int i, result = 0;
        for (i = 0; i < bin.length(); i++) {
            result <<= 1;
            if (bin.charAt(i) == '1')
                result++;
            }
        return result;
    }
    
    public static Transport_Packet criaTP(Transport_Packet tp, byte[] b) {

        tp.package_full = b;
        tp.sync_byte = b[0] & 0xFF;
        tp.transport_error_indicator = ((b[1] & 0xFF) >> 7) & 0x01;
        tp.payload_unit_start_indicator = ((b[1] & 0xFF) >> 6) & 0x01;
        tp.transport_priority = ((b[1] & 0xFF) >> 5) & 0x01;
        tp.PID = binToDec(Integer.toBinaryString((b[1] & 0xFF) & 0x1f) + Integer.toBinaryString(b[2] & 0xFF)) & 0x1FFF;
        tp.transport_scrambling_control = ((b[3] & 0xFF) >> 6) & 0x03;
        tp.adaptation_field_control = ((b[3] & 0xFF) >> 4) & 0x03;
        tp.continuity_counter = (b[3] & 0xFF) & 0xF;
        Program_Association_Table pat;
        if (tp.payload_unit_start_indicator == 1 && tp.PID == 0 && tp.adaptation_field_control == 1) {
            pat = new Program_Association_Table();
            pat.table_id = b[5] & 0xFF;
            pat.section_syntax_indicator = ((b[6] & 0xFF) >> 7) & 0x01;
            pat.zero = ((b[6] & 0xFF) >> 6) & 0x01;
            pat.reserved1 = ((b[6] & 0xFF) >> 4) & 0x03;
            pat.section_length = binToDec(Integer.toBinaryString(((b[6] & 0xFF) & 0xF)) + Integer.toBinaryString(b[7] & 0xFF)) & 0xFFF;
            pat.transport_stream_id = binToDec(Integer.toBinaryString(b[8] & 0xFF) + Integer.toBinaryString(b[9] & 0xFF)) & 0xFFFF;
            pat.reserved2 = ((b[10] & 0xFF) >> 6) & 0x03;
            pat.version_number = ((b[10] & 0xFF) >> 1) & 0x1F;
            pat.current_next_indicator = (b[10] & 0xFF) & 0x01;
            pat.section_number = b[11] & 0xFF;
            pat.last_section_number = b[12] & 0xFF;
            int num = (pat.section_length - 8) / 4;
            if (num <= 0) {
                num = 0;
            } else {
                pat.program_map_PID = new int[num];
            }
            int aux = 13;
            for (int j = 0; j < num; j++) {
                if ((binToDec(Integer.toBinaryString(b[aux] & 0xFF) + Integer.toBinaryString(b[aux + 1] & 0xFF)) & 0xFFFF) != 0) {
                    pat.program_map_PID[j] = binToDec(Integer.toBinaryString((b[aux + 2] & 0xFF) & 0x1F) + Integer.toBinaryString((b[aux + 3] & 0xff))) & 0x1FFF;
                    }
                    aux = aux + 4;
            }
            pat.CRC_32 = binToDec(Integer.toBinaryString(b[aux] & 0xFF) + Integer.toBinaryString(b[aux+1] & 0xFF) + Integer.toBinaryString(b[aux+2] & 0xFF) + Integer.toBinaryString(b[aux+3] & 0xFF));
            tp.data_byte = pat;
        }
        return tp;
    }
    
    public static List<Integer> getPIDS(List<Transport_Packet> listaTP) {
            ArrayList<Integer> listaPID = new ArrayList<Integer>();
            int aux = 0;
            for (int i = 0; i < listaTP.size(); i++) {

                    if ((listaTP.get(i).data_byte != null) && (listaTP.get(i).data_byte.program_map_PID[0] != aux)) {
                        aux = listaTP.get(i).data_byte.program_map_PID[0];
                        listaPID.add(listaTP.get(i).data_byte.program_map_PID[0]);
                    }
            }
            return listaPID;
    }
    
    public static ArrayList<Program_Map_Table> tabelaPMT(ArrayList<Integer> listaPID) throws IOException {
        File file = new File("C:\\Users\\Rodolfo\\Downloads\\video.ts");
        ArrayList<Program_Map_Table> listaPMT = new ArrayList<Program_Map_Table>();
        for (int j = 0; j < listaPID.size(); j++) {
            FileInputStream fi = new FileInputStream(file);
            byte[] b = new byte[188];
            int i = 0;
            while ((i = fi.read(b)) > -1) {
                if ((binToDec(Integer.toBinaryString((b[1] & 0xFF) & 0x1f) + Integer.toBinaryString(b[2] & 0xFF)) & 0x1FFF) == listaPID.get(j)) {
                    Program_Map_Table pmt = new Program_Map_Table();
                    pmt.table_id = b[5] & 0xFF;
                    pmt.section_syntax_indicator = ((b[6] & 0xFF) >> 7) & 0x01;
                    pmt.zero = ((b[6] & 0xFF) >> 6) & 0x01;
                    pmt.reserved1 = ((b[6] & 0xFF) >> 4) & 0x03;
                    pmt.section_length = binToDec(Integer.toBinaryString((b[6] & 0xFF) & 0xF) + Integer.toBinaryString(b[7] & 0xFF));
                    pmt.program_number = binToDec(Integer.toBinaryString(b[8] & 0xFF)+ Integer.toBinaryString(b[9] & 0xFF)) & 0xFFFF;
                    pmt.reserved2 = ((b[10] & 0xFF) >> 6) & 0x03;
                    pmt.version_number = ((b[10] & 0xFF) >> 1) & 0x1f;
                    pmt.current_next_indicator = (b[10] & 0xFF) & 0x01;
                    pmt.section_number = (b[11] & 0xFF);
                    pmt.last_section_number = (b[12] & 0xFF);
                    pmt.reserved3 = ((b[13] & 0xFF) >> 5) & 0x7;
                    pmt.PCR_PID = binToDec(Integer.toBinaryString((b[13] & 0xFF) & 0x1F)+ Integer.toBinaryString(b[14] & 0xFF)) & 0x1FFF;
                    pmt.reserved4 = ((b[15] & 0xFF) >> 4) & 0xF;
                    pmt.program_info_length = b[16] & 0xFF;
                    int num = (pmt.section_length - 13) / 5;
                    pmt.stream_type = new int[num];
                    pmt.reserved5 = new int[num];
                    pmt.elementary_PID = new int[num];
                    pmt.reserved6 = new int[num];
                    pmt.ES_info_length = new int[num];
                    int aux = 17;
                    for (int l = 0; l < num; l++) {
                        pmt.stream_type[l] = b[aux] & 0xFF;
                        pmt.reserved5[l] = ((b[aux + 1] & 0xFF) >> 5) & 0x7;
                        pmt.elementary_PID[l] = binToDec(Integer.toBinaryString((b[aux + 1] & 0xFF) & 0x1F) + Integer.toBinaryString(b[aux + 2] & 0xFF)) & 0x1FFF;
                        pmt.reserved6[l] = ((b[aux + 3] & 0xFF) >> 4) & 0xF;
                        pmt.ES_info_length[l] = binToDec(Integer.toBinaryString((b[aux + 3] & 0xFF) & 0xF) + Integer.toBinaryString(b[aux + 4] + 0xFF)) & 0xFFF;
                        aux = aux + 5;
                    }
                    pmt.CRC_32 = binToDec(Integer.toBinaryString(b[aux] & 0xFF)+ Integer.toBinaryString(b[aux + 1] & 0xFF) + Integer.toBinaryString(b[aux + 2] & 0xFF) +Integer.toBinaryString(b[aux+3] & 0xFF));
                    listaPMT.add(pmt);
                }
            }
            fi.close();
        }
        return listaPMT;
    }
    
    public static ArrayList<Program_Association_Table> tabelaPAT(List<Transport_Packet> listaTP) {
        ArrayList<Program_Association_Table> listaPAT = new ArrayList<Program_Association_Table>();
        for (Transport_Packet tp : listaTP) {
            if (tp.data_byte != null) {
                listaPAT.add(tp.data_byte);
            }
        }
        return listaPAT;
    }
}
