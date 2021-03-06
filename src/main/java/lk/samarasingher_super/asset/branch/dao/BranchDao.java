package lk.samarasingher_super.asset.branch.dao;

import lk.samarasingher_super.asset.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchDao extends JpaRepository<Branch, Integer> {
}
