CREATE TABLE IF NOT EXISTS public.tenants
(
    id_tenant uuid NOT NULL DEFAULT gen_random_uuid(),
    name character varying(255) not null,
    email character varying(255),
    website character varying(255),
    endpoint character varying(255) not null,
    phone character varying(255),
    logo character varying(255),
    CONSTRAINT tenants_pkey PRIMARY KEY (id_tenant)
);

CREATE OR REPLACE PROCEDURE public.p_register_new_tenant(
   IN in_company_name character varying,
   IN in_company_address character varying,
   IN in_company_website character varying,
   IN in_company_email character varying,
   IN in_company_phone character varying,
   IN in_company_logo character varying,
   IN in_endpoint character varying,
   IN in_first_name character varying,
   IN in_last_name character varying,
   IN in_email character varying,
   IN in_password character varying,
   OUT out_message character varying,
   OUT out_success boolean,
   OUT out_create_user boolean,
   OUT out_schema_name character varying
)
language plpgsql
as $$ 
 DECLARE
	ln_count_schema integer;
	
	--tenant
	lu_tenant_id uuid;
	
	--user & role
	lu_id_user uuid;
	lu_id_role uuid;
	
	--schema name
	ls_schema_name varchar(255);
	
  BEGIN
    EXECUTE FORMAT('SET search_path TO public;');
	
	--SELECT count(1) into ln_count_schema FROM information_schema.schemata 
	--WHERE schema_name = in_tenant_schema;
	
	SELECT COUNT(1) INTO ln_count_schema FROM tenants
	WHERE endpoint = in_endpoint;
	
	--jika sudah ada schema, maka hanya tambahkan user
	IF(ln_count_schema >= 1) THEN
		out_message := 'schema gagal dibuat';
		out_success := false;
		out_create_user := false;
		out_schema_name := '';
	ELSE 
		
		--insert new tenant
		INSERT INTO tenants(name, email, website, endpoint, phone, logo) 
		VALUES(in_company_name, in_company_email, in_company_website, in_endpoint, in_company_phone, in_company_logo)
		RETURNING id_tenant INTO lu_tenant_id;
		
		ls_schema_name := replace(lu_tenant_id::text,'-',''); --schema name is from id_tenant but without "-"
		
		EXECUTE FORMAT('CREATE SCHEMA IF NOT EXISTS %I;', ls_schema_name);
		EXECUTE FORMAT('SET search_path TO %I;', ls_schema_name);
		
		out_schema_name := ls_schema_name;
			
		CREATE TABLE IF NOT EXISTS users
		(
			id_user uuid NOT NULL DEFAULT gen_random_uuid(),
			first_name character varying(255) not null,
			last_name character varying(255),
			email character varying(255) not null,
			password character varying(255) not null,
			image character varying(255),
			CONSTRAINT users_pkey PRIMARY KEY (id_user)
		);

		CREATE TABLE IF NOT EXISTS roles
		(
			id_role uuid NOT NULL DEFAULT gen_random_uuid(),
			name character varying(255) not null,
			CONSTRAINT roles_pkey PRIMARY KEY (id_role)
		);

		CREATE TABLE IF NOT EXISTS users_roles
		(
			id_user uuid,
			id_role uuid,
			CONSTRAINT pk_users_roles PRIMARY KEY(id_user, id_role),
			CONSTRAINT fk_users FOREIGN KEY(id_user) REFERENCES users(id_user)
			ON DELETE CASCADE,
			CONSTRAINT fk_roles FOREIGN KEY(id_role) REFERENCES roles(id_role)
			ON DELETE CASCADE
		);
		
		out_message := 'schema ' || ls_schema_name || ' berhasil dibuat';
		out_success := true;
		
		
		-- insert new user and role;
		INSERT INTO roles(name) VALUES('admin') RETURNING id_role INTO lu_id_role;
		INSERT INTO roles(name) VALUES('manager');
		INSERT INTO roles(name) VALUES('operator');
		
		INSERT INTO users(first_name, last_name, email, password, image) 
		VALUES(in_first_name, in_last_name, in_email, in_password, 'profile.png') RETURNING id_user INTO lu_id_user;
		
		INSERT INTO users_roles(id_user, id_role) VALUES(lu_id_user, lu_id_role);
		
		out_message := out_message || '. user ' || in_email || ' berhasil dibuat ';
		out_create_user := true;
		
		EXECUTE FORMAT('SET search_path TO public;');
	END IF;
end;$$