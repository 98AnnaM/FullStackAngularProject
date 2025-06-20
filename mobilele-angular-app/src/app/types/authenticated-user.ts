import {AuthoritiesEnum} from '../enums/authorities-enum';

export interface AuthenticatedUser{
  token: string;
  email: string;
  firstName: string;
  lastName: string;
  authorities: AuthoritiesEnum[];
}
