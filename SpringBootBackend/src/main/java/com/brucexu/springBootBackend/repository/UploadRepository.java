package com.brucexu.springBootBackend.repository;

import com.brucexu.springBootBackend.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<Upload, Long> {
}
