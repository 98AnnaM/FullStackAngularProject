import {AuthoritiesEnum} from '../enums/authorities-enum';

export interface UserLoginResponse {
  token: string;
  email: string;
  firstName: string;
  lastName: string;
  authorities: AuthoritiesEnum[];
}
