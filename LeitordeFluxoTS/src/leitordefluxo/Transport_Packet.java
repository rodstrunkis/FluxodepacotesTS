/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitordefluxo;

/**
 *
 * @author Rodolfo
 */
public class Transport_Packet {
    byte[] package_full;
    int sync_byte;
    int transport_error_indicator;
    int payload_unit_start_indicator;
    int transport_priority;
    int PID;
    int transport_scrambling_control;
    int adaptation_field_control;
    int continuity_counter;
    Program_Association_Table data_byte;

    @Override
    public String toString() {
            return "TransportStreamPackage [PID=" + PID
                            + ", adaptation_field_control=" + adaptation_field_control
                            + ", continuity_counter=" + continuity_counter + ", payload_unit_start_indicator="
                            + payload_unit_start_indicator + ", sync_byte=" + sync_byte
                            + ", transport_error_indicator=" + transport_error_indicator
                            + ", transport_priority=" + transport_priority
                            + ", transport_scrambling_control="
                            + transport_scrambling_control + "]";
    }
}
