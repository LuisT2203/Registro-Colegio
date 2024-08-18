/*
 * package com.colegio.demo;
 * 
 * import java.util.Collection;
 * 
 * import org.springframework.security.core.GrantedAuthority; import
 * org.springframework.security.core.userdetails.UserDetails;
 * 
 * import com.colegio.demo.modelo.Usuario;
 * 
 * @SuppressWarnings("serial") public class UsuarioDetails implements
 * UserDetails {
 * 
 * private Usuario usuario;
 * 
 * public UsuarioDetails(Usuario usuario) { this.usuario = usuario; }
 * 
 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
 * //TODO Auto-generated method stub return null; }
 * 
 * @Override public String getPassword() {
 * 
 * return usuario.getClave(); }
 * 
 * @Override public String getUsername() {
 * 
 * return usuario.getCorreo(); }
 * 
 * @Override public boolean isAccountNonExpired() { // TODO Auto-generated
 * method stub return true; }
 * 
 * @Override public boolean isAccountNonLocked() { // TODO Auto-generated method
 * stub return true; }
 * 
 * @Override public boolean isCredentialsNonExpired() { // TODO Auto-generated
 * method stub return true; }
 * 
 * @Override public boolean isEnabled() { // TODO Auto-generated method stub
 * return true; }
 * 
 * }
 */
 