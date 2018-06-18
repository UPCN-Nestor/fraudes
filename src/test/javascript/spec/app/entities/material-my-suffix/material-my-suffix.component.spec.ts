/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { MaterialMySuffixComponent } from '../../../../../../main/webapp/app/entities/material-my-suffix/material-my-suffix.component';
import { MaterialMySuffixService } from '../../../../../../main/webapp/app/entities/material-my-suffix/material-my-suffix.service';
import { MaterialMySuffix } from '../../../../../../main/webapp/app/entities/material-my-suffix/material-my-suffix.model';

describe('Component Tests', () => {

    describe('MaterialMySuffix Management Component', () => {
        let comp: MaterialMySuffixComponent;
        let fixture: ComponentFixture<MaterialMySuffixComponent>;
        let service: MaterialMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [MaterialMySuffixComponent],
                providers: [
                    MaterialMySuffixService
                ]
            })
            .overrideTemplate(MaterialMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MaterialMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.materials[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
