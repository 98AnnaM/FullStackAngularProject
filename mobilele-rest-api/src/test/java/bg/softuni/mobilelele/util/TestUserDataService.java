package bg.softuni.mobilelele.util;

import bg.softuni.mobilelele.model.user.MobileleUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class TestUserDataService implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new MobileleUserDetails(
            "12345",
             username,
            "Test",
            "User",
             Collections.emptyList());
  }
}
