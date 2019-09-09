INSERT INTO
  oauth_client_details (
    client_id,
    client_secret,
    resource_ids,
    scope,
    authorized_grant_types,
    authorities,
    access_token_validity,
    refresh_token_validity
  )
VALUES
  (
    'appclient',
    '$2a$08$ePUWmsLTqNezRk7MCUfg6.HU3RUO3N2M6H.Xj0gMvKiUsGgvg/Fve',
    'demoapp',
    'read,write',
    'authorization_code,check_token,refresh_token,password',
    'ROLE_CLIENT',
    3000,
    250000
  );
  
INSERT INTO users (username,password,enabled) 
    VALUES ('admin0', '$2a$10$xzOT7TENBA8vCR9T9/4wA.Y6x3gEZG8dn56Ce7G3ZQvh1LEMR/NQi', TRUE);
INSERT INTO users (username,password,enabled) 
    VALUES ('user0','$2a$10$RbHpBHBNvQCxEqmhwlQh.uswOPbgw9Fh2EDIAzVac7XlxnYYWFDie', TRUE);
  
INSERT INTO groups (id, group_name) VALUES (1, 'jwtDEMOAPP_USER_AND_ADMIN_GROUP');
INSERT INTO groups (id, group_name) VALUES (2, 'jwtDEMOAPP_USER_ONLY_GROUP');

INSERT INTO group_authorities (group_id, authority) VALUES (1, 'AUTHORIZED_DEMOAPP_USER');
INSERT INTO group_authorities (group_id, authority) VALUES (1, 'AUTHORIZED_DEMOAPP_ADMIN');

INSERT INTO group_authorities (group_id, authority) VALUES (2, 'AUTHORIZED_DEMOAPP_USER');

INSERT INTO group_members (username, group_id) VALUES ('admin0', 1);
INSERT INTO group_members (username, group_id) VALUES ('user0', 2);  