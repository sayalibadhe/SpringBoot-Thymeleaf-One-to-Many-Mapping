package com.poc7.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Project extends AuditModel {
	
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	 private Integer projectId;
	 
	 @NotNull
	 @Lob
	 private String projectName;
	
	 @NotNull
	 private String technology;
	 
	 
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	
	 @JoinColumn(name = "student_id", nullable = false)
	 @OnDelete(action = OnDeleteAction.CASCADE)
	 @JsonIgnore
	  
	 private Student student;
	 
	

}
