package com.somafit.somafit_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime accessTime;

    private Boolean accessGranted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
