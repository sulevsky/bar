import { Injectable } from "@angular/core";
import "rxjs/add/operator/filter";
import {Observable} from "rxjs";
import { Company } from './company.interface';

@Injectable()
export class CompanyService {

  constructor() {
    localStorage.setItem('mockStorage', JSON.stringify([]));
  }

  public getCompanies(): Observable<Company[]> {

    const storage = JSON.parse(localStorage.getItem('mockStorage'));

    return Observable.of(storage);
  }

  public getCompany(id: string): Observable<Company> {

    const storage = JSON.parse(localStorage.getItem('mockStorage'));

    return Observable.of(
      storage.filter(item => item.id === id)[0]
    )
  }

  public createCompany(company: Company): Observable<Company> {

    const storage = JSON.parse(localStorage.getItem('mockStorage'));
    storage.push(company);
    localStorage.setItem('mockStorage', JSON.stringify(storage));

    return Observable.of(company);
  }

}
