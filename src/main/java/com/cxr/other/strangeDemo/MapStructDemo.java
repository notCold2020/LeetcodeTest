//package com.cxr.other.strangeDemo;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @Author: CiXingrui
// * @Create: 2021/10/26 11:32 上午
// */
//@RestController
//public class MapStructDemo {
//
//    @Resource
//    private Do2Dto do2Dto;
//
//    @RequestMapping("/MapStructDemo")
//    public void test() {
////        Bean1 bean2 = do2Dto.bean2ToBean1(new Bean2("12345"));
//
//        Doctor doctor = new Doctor();
//        doctor.setPatientList(Arrays.asList(new Patient(1,"1")));
//        DoctorDto doctorDto = do2Dto.doctorToDto(doctor);
//    }
//
//}
//
//
//@Mapper(componentModel = "spring")
//interface Do2Dto {
//
//    /**
//     * 就算把name映射到age
//     * 如果两个bean都有name字段 还是会被映射
//     */
//    @Mapping(source = "name", target = "age")
//    Bean1 bean2ToBean1(Bean2 bean2);
//
//    @Mappings(
//            @Mapping(source = "bean3.b3" ,target = "bean4.b4")
//    )
//    Bean4 bean3ToBean4(Bean3 bean3);
//
//
//    /**
//     * 执行自定义的方法
//     */
//    @Mappings(
//            @Mapping(target = "patientDtoList",expression = "java(doctorToDtoList(doctor.getPatientList()))")
//    )
//    DoctorDto doctorToDto(Doctor doctor);
//    default List<PatientDto> doctorToDtoList(List<Patient> patientList){
//        List<PatientDto> patientDtoList = new ArrayList<>();
//
//        patientDtoList.add(new PatientDto(12,"12"));
//        return patientDtoList;
//    }
//
//}
//
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Bean1 {
//    private String name;
//    private Integer age;
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Bean2 {
//    private String name;
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//class Bean3 {
//    private List<Doctor> b3;
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//class Bean4 {
//    private List<DoctorDto> b4;
//}
//
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Doctor {
//    private int id;
//    private String name;
//    private String specialty;
//    private List<Patient> patientList;
//    // getters and setters or builder
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class DoctorDto {
//    private int id;
//    private String name;
//    private String degree;
//    private String specialization;
//    private List<PatientDto> patientDtoList;
//    // getters and setters or builder
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Patient {
//    private int id;
//    private String name;
//    // getters and setters or builder
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class PatientDto {
//    private int id;
//    private String name;
//    // getters and setters or builder
//}
