package com.vitmedics.vitcheck.model.entities.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "client_smtp")
public class ClientSmtp {

    @Id
    @Column(length = 500, nullable = false)
    private String username;

    @Column(length = 1000, nullable = false)
    private String pwd;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name="sender_address", length = 500, nullable = false)
    private String senderAddress;

    @Column(name="smtp_port", nullable = false)
    private int smtpPort;

    @Column(name="smtp_host", length = 500, nullable = false)
    private String smtpHost;
}
