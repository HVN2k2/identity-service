package com.hvnhuan.identity_service.service;

import com.hvnhuan.identity_service.dto.request.AppointmentUpdateRequest;
import com.hvnhuan.identity_service.dto.request.AppoitmentCreationRequet;
import com.hvnhuan.identity_service.entity.Appointment;
import com.hvnhuan.identity_service.exception.AppException;
import com.hvnhuan.identity_service.exception.ErrorCode;
import com.hvnhuan.identity_service.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppoitment(AppoitmentCreationRequet requet){
        Appointment appointment = new Appointment();
        if(appointmentRepository.existsByTitle(requet.getTitle())){
            throw new AppException(ErrorCode.APPOINTMENT_EXITED);
        }
        appointment.setTitle(requet.getTitle());
        appointment.setDetail(requet.getDetail());
        appointment.setDate(requet.getDate());

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointments(){
        return appointmentRepository.findAll();
    }

    public Appointment getAppoitment(String id){
        return appointmentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Not found appointment!"));
    }

    public Appointment updateAppointment(String id, AppointmentUpdateRequest request){
        Appointment appointment = getAppoitment(id);
        appointment.setTitle(request.getTitle());
        appointment.setDetail(request.getDetail());
        appointment.setDate(request.getDate());

        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(String id){
        appointmentRepository.deleteById(id);
    }
}
