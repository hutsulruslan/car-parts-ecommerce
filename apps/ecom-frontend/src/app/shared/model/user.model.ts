export interface BaseUrl {
  firstName?: string;
  lastName?: string;
  email?: string,
  publicId?: string;
}

export interface ConnectedUser extends BaseUrl {
  authorities?: string[];
}
